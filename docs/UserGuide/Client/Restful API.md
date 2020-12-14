<!--

    Licensed to the Apache Software Foundation (ASF) under one
    or more contributor license agreements.  See the NOTICE file
    distributed with this work for additional information
    regarding copyright ownership.  The ASF licenses this file
    to you under the Apache License, Version 2.0 (the
    "License"); you may not use this file except in compliance
    with the License.  You may obtain a copy of the License at
    
        http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations
    under the License.

-->

# Restful API

### Basic usage of restful api

Before you use restful api, you have to login. 

```
curl http://localhost:8282/user/login?username=root&password=root
```

Then insert some values.

```
curl -H "Content-Type: application/json" -X POST  --data '[{"deviceId": "root.ln.wf01.wt01","measurements": ["temperature","status"],"timestamps": 1,"values": [1.1,false]}]' http://localhost:8282/insert
```



check details in [Restful doc](https://documenter.getpostman.com/view/12320024/T1LLDnuj).