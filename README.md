# proteus-to-http-demo
Simple demo showing HTTP Restful calls bridge to a Proteus Spring Application
## Projects
This repo contains the following projects:

* [http-service](http-service) - http service that bridges http requests to the string, number, and color services
* [char-service](char-service) - returns a randoms character stream
* [color-service](color-service) - returns a random color
* [number-service](number-service) - returns a random number of between two numbers
* [string-service](string-service) - returns a random string of a requested size

## Prerequisites
The Proteus to HTTP Demo requires you have the following items installed on your machine:

* [Docker](https://docs.docker.com/install/)

## Getting Started
Follow the steps below to get a client and service communicating via Proteus in just a few short minutes.

1. In a new terminal window, pull the latest Proteus Broker Docker image by running the following command:

        docker pull netifi/proteus:1.5.2
        
2. Next, run the following command to start the Proteus Broker:

        docker run \
        -p 8001:8001 \
        -p 7001:7001 \
        -p 9000:9000 \
        -e BROKER_SERVER_OPTS="'-Dnetifi.authentication.0.accessKey=9007199254740991'  \
        '-Dnetifi.broker.console.enabled=true' \
        '-Dnetifi.authentication.0.accessToken=kTBDVtfRBO4tHOnZzSyY5ym2kfY=' \
        '-Dnetifi.broker.admin.accessKey=9007199254740991' \
        '-Dnetifi.broker.admin.accessToken=kTBDVtfRBO4tHOnZzSyY5ym2kfY='" \
        netifi/proteus:1.5.2

## Bugs and Feedback
For bugs, questions, and discussions please use the [Github Issues](https://github.com/netifi/proteus-spring-quickstart/issues).

## License
Copyright 2018 [Netifi Inc.](https://www.netifi.com)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
