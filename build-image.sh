#!/bin/bash

BLUE='\033[1;34m'
NC='\033[0m' # No Color

./build.sh

echo -e "${BLUE}CRIANDO IMAGEM DO DOCKER${NC}"
docker build -f Dockerfile --force-rm -t despesas-service .