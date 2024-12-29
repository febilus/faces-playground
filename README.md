# Faces Playground

A Jakarta EE application with some faces examples.
It uses the wildfly-jar plugin to create a bootable jar

## Run Locally

Go to the project directory

```bash
  cd faces-playground
```

### Start the server in dev-watch

Start with recompile, repackage and redeploy the wildfly. With depug port 8787

```bash
  mvn clean package wildfly-jar:dev-watch -Dwildfly.bootable.debug=true
```

### Build a bootable jar

Build a bootable jar and start it

```bash
  mvn clean package wildfly-jar:package
  java -jar target/faces-playground-bootable.jar
```


You can access the project at http://localhost:8080/

