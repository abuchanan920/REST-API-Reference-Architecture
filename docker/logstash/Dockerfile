FROM      dockerfile/java
MAINTAINER Hibu User "user@hibu.com"

ENV DEBIAN_FRONTEND noninteractive

# What tag to use for lumberjack
ENV LUMBERJACK_TAG MYTAG

# Number of elasticsearch workers
ENV ELASTICWORKERS 1

RUN wget https://download.elasticsearch.org/logstash/logstash/logstash-1.4.2.tar.gz -O /tmp/logstash.tar.gz --no-check-certificate 2>/dev/null

RUN tar Cxvf /opt /tmp/logstash.tar.gz

ADD run.sh /usr/local/bin/run.sh
RUN chmod +x /usr/local/bin/run.sh

RUN mkdir /opt/certs/
ADD certs/logstash-forwarder.crt /opt/certs/logstash-forwarder.crt
ADD certs/logstash-forwarder.key /opt/certs/logstash-forwarder.key
ADD collectd-types.db /opt/collectd-types.db

EXPOSE 514
EXPOSE 5043
EXPOSE 9200
EXPOSE 9292
EXPOSE 9300

CMD /usr/local/bin/run.sh
