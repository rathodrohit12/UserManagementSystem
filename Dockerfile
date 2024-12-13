FROM ubuntu:latest
LABEL authors="rohit"

ENTRYPOINT ["top", "-b"]