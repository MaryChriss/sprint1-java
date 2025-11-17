# futureStack 

Sistema inteligente de rastreamento de motos via Wi-Fi, com mapeamento digital em tempo real e adaptável a diferentes filiais.

## 📋 Descrição da Solução

O **futureStack** é uma solução de monitoramento de motos que utiliza gateways Wi-Fi posicionados estrategicamente para detectar automaticamente a presença de veículos em duas zonas principais: **Zona A (Pátio)** e **Zona B (Manutenção)**.

Cada moto emite sinal que é captado pelo **gateway instalado em cada zona**. Com base na intensidade do sinal (`RSSI`), o sistema identifica a localização aproximada da moto e atualiza sua posição em um **mapa digital interativo**. Além disso, são apresentados dados como **metragem total de cada zona**, **ocupação atual** e uma **visualização detalhada em tempo real**.

O sistema também permite **buscas por placa ou modelo**, e é totalmente **adaptável a diferentes filiais**, com cadastro personalizado da metragem de pátio e manutenção, além de gateways exclusivos por local.

## Essa API permite:

- Cadastro de zonas com metragem por filial
- Busca por **placa** ou **zona**
- Paginação, ordenação e filtros
- Tratamento global de erros
- Validação de campos com Bean Validation

## 🚀 Tecnologias Utilizadas

- Java 17
- Spring Boot 
- Spring Web
- Spring Data JPA
- Spring Cache (com Caffeine)
- Spring Validation (Jakarta Bean Validation)
- Banco H2 (ou Oracle)
- Lombok
- MapStruct (ou ModelMapper)
- Swagger / OpenAPI (Springdoc)

## 🛠️ Como Rodar o Projeto Localmente

1. **Clone o repositório:**

```bash
git clone https://github.com/seu-usuario/sprint1-java.git
cd sprint1-java
```

2. **Configure o banco H2**

3. **rode a aplicação**

## 🧠 Funcionalidades Futuras

- Integração com API dos gateways IoT
- Integração com o front-end
- 
Autenticação e controle de acesso por filial
## 👥 Integrantes

- Mariana Christina RM: 554773
- Gabriela Moguinho RM: 556143
- Henrique Maciel RM: 556480
