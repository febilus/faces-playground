# Faces Playground

A Jakarta EE application with some faces examples.
It uses the [wildfly-maven-plugin](https://docs.wildfly.org/wildfly-maven-plugin/) to deploy the wildfly Server.

## Run Locally

Go to the project directory

```bash
  cd faces-playground
```

### Buld and Start in Debug Mode

Start with recompile, repackage and redeploy the wildfly. With depug port 8787
The application will be redeployed for every change.

```bash
  mvn clean package wildfly:dev -Dwildfly.debug=true -Dwildfly.debug.port=8787  -Dwildfly.debug.suspend=true
```

### Start the server via bootable jar via 

```bash
  mvn clean package wildfly:start-jar
```

### Stop application

```bash
  mvn wildfly:shutdown
```

You can access the project at http://localhost:8080/

