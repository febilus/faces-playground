package de.play.faces;

import jakarta.el.ELException;
import jakarta.faces.FacesException;
import jakarta.faces.application.Application;
import jakarta.faces.application.ViewExpiredException;
import jakarta.faces.application.ViewHandler;
import jakarta.faces.component.UIViewRoot;
import jakarta.faces.context.ExceptionHandler;
import jakarta.faces.context.ExceptionHandlerWrapper;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ExceptionQueuedEvent;
import jakarta.faces.view.ViewDeclarationLanguage;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class CustomExceptionHandler extends ExceptionHandlerWrapper {

    public CustomExceptionHandler(ExceptionHandler wrapped) {
        super(wrapped);
    }

    @Override
    public void handle() throws FacesException {
        handleException(FacesContext.getCurrentInstance());
        super.handle();
    }

    private void handleException(FacesContext facesContext) {
        Iterator<ExceptionQueuedEvent> unhandledExceptionQueuedEvents = getUnhandledExceptionQueuedEvents().iterator();

        if (facesContext == null
                || facesContext.getExternalContext().isResponseCommitted()
                || !unhandledExceptionQueuedEvents.hasNext()) {
            return;
        }

        Throwable exception = unhandledExceptionQueuedEvents.next().getContext().getException();

        while (exception.getCause() != null && (exception instanceof FacesException || exception instanceof ELException)) {
            exception = exception.getCause();
        }

        ExternalContext external = facesContext.getExternalContext();

        String uri = external.getRequestContextPath() + external.getRequestServletPath();
        Map<String, Object> requestScope = external.getRequestMap();
        requestScope.put(RequestDispatcher.ERROR_REQUEST_URI, uri);
        requestScope.put(RequestDispatcher.ERROR_EXCEPTION, exception);

        String viewId;
        if (exception instanceof ViewExpiredException) {
            viewId = "/WEB-INF/errorpages/expired.xhtml";
        } else {
            viewId = "/WEB-INF/errorpages/500.xhtml";
        }

        Application application = facesContext.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        UIViewRoot viewRoot = viewHandler.createView(facesContext, viewId);
        facesContext.setViewRoot(viewRoot);

        try {
            external.responseReset();
            if (!facesContext.getPartialViewContext().isAjaxRequest()) {
                external.setResponseStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            ViewDeclarationLanguage viewDeclarationLanguage = viewHandler.getViewDeclarationLanguage(facesContext, viewId);
            viewDeclarationLanguage.buildView(facesContext, viewRoot);
            facesContext.getPartialViewContext().setRenderAll(true);
            viewDeclarationLanguage.renderView(facesContext, viewRoot);
            facesContext.responseComplete();
        } catch (IOException e) {
            throw new FacesException(e);
        } finally {
            requestScope.remove(RequestDispatcher.ERROR_EXCEPTION);
        }

        unhandledExceptionQueuedEvents.remove();

        while (unhandledExceptionQueuedEvents.hasNext()) {
            unhandledExceptionQueuedEvents.next();
            unhandledExceptionQueuedEvents.remove();
        }
    }

}
