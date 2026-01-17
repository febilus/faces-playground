package de.play.cache;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class TempDirDataCache<T> {

    public abstract String getTempFileName();

    public abstract T loadFromNetwork();

    private Path cacheFile = Paths.get(System.getProperty("java.io.tmpdir"), getTempFileName());

    public T findTempCache() {
        T cache = null;
        if (Files.exists(cacheFile)) {
            try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(cacheFile))) {
                cache = (T) ois.readObject();
            } catch (Exception e) {
                cache = loadFromNetwork();
                saveCache(cache);
            }
        } else {
            cache = loadFromNetwork();
            saveCache(cache);
        }
        return cache;
    }

    private void saveCache(T cache) {
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(cacheFile))) {
            oos.writeObject(cache);
        } catch (Exception e) {
            Logger.getLogger(getClass().getSimpleName()).log(Level.SEVERE, "saving failed", e);
        }
    }

}
