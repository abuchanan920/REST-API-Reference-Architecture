FROM ubuntu

ENV GOPATH /var/lib/golang
ENV PATH $GOPATH/bin:$PATH

RUN mkdir $GOPATH

RUN apt-get update
RUN apt-get install -y golang mercurial git

RUN go get github.com/elasticsearch/logstash-forwarder \
 && go get github.com/tools/godep \
 && godep get github.com/digital-wonderland/docker-logstash-forwarder

RUN mkdir /mnt/logstash-forwarder
ADD certs/logstash-forwarder.crt /mnt/logstash-forwarder/logstash-forwarder.crt
ADD certs/logstash-forwarder.key /mnt/logstash-forwarder/logstash-forwarder.key

ENTRYPOINT ["/var/lib/golang/bin/docker-logstash-forwarder"]