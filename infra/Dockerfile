FROM quay.io/wildfly/wildfly:30.0.0.Final-jdk17

USER root
COPY module/org /opt/jboss/wildfly/modules/system/layers/base/org
COPY install-datasource.cli /opt/jboss/wildfly/
COPY enable-ejb-remoting.cli /opt/jboss/wildfly/
COPY entrypoint.sh /entrypoint.sh

RUN chown -R jboss:jboss /opt/jboss/wildfly/
RUN chmod +x /entrypoint.sh

USER jboss
RUN /opt/jboss/wildfly/bin/jboss-cli.sh --file=/opt/jboss/wildfly/install-datasource.cli
RUN /opt/jboss/wildfly/bin/jboss-cli.sh --file=/opt/jboss/wildfly/enable-ejb-remoting.cli || true
RUN /opt/jboss/wildfly/bin/add-user.sh admin Admin#123 --silent

ENTRYPOINT ["/entrypoint.sh"]