FROM ubuntu:latest
LABEL authors="quand"

ENTRYPOINT ["top", "-b"]