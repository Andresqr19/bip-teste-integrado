#!/bin/bash
/opt/jboss/wildfly/bin/add-user.sh admin Admin#123 --silent

/opt/jboss/wildfly/bin/jboss-cli.sh --file=/opt/jboss/wildfly/enable-ejb-remoting.cli

/opt/jboss/wildfly/bin/standalone.sh -b 0.0.0.0 -bmanagement=0.0.0.0
