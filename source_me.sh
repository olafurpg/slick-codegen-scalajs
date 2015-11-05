#!/usr/bin/env bash
export DB_NAME="postgres"
export APPLICATION_SECRET="changeme"
export DB_DEFAULT_URL="jdbc:postgresql://`docker-ip`:5432/$DB_NAME"
export DB_DEFAULT_USER="postgres"
export DB_DEFAULT_PASSWORD="postgres"
export DB_HOST="`docker-ip`"

