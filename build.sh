#!/bin/bash

BLUE='\033[1;34m'
NC='\033[0m' # No Color

echo -e "${BLUE}PARANDO IMAGEM DO CONTAINER DE TESTE${NC}"
docker-compose -f docker-compose.test.yml down

echo -e "${BLUE}REMOVENDO DADOS DO BANCO DE TESTE${NC}"
rm -Rf ./docker/postgres/data-despesa-test
echo -e "${BLUE}INICIANDO CONTAINER DE TESTE${NC}"
docker-compose -f docker-compose.test.yml up -d
echo -e "${BLUE}REMOVENDO DADOS DO BANCO DE TESTE${NC}"
mvn clean package
echo -e "${BLUE}PARANDO IMAGEM DO CONTAINER DE TESTE${NC}"
docker-compose -f docker-compose.test.yml down