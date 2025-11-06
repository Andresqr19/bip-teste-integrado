#!/bin/bash
cd ..
mvn -pl ejb-module clean package

cp ejb-module/target/ejb-module-1.0.0-SNAPSHOT.jar wildfly/deployments/

echo "✅ EJB deployado no WildFly!"