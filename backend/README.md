# 🌤 Documentação da API do modclima

## 🛣 Rotas

As rotas da aplicação são as seguintes:

### 🚏 `/mills`

* **POST**: rota que permite que uma nova usina seja criada. O corpo da requisição é um JSON com a seguinte forma:

````json
{
  "name": "Usina"
}
````

<details>
    <summary>Retorno</summary>
    <pre>
        <code>
            {
                "id": 1,
                "name": "Usina",
                "harvests": []
            }
        </code>
    </pre>
</details>

* **GET**: rota que permite realizar uma listagem de todas as usinas cadastradas. Além disso, ela permite realizar
  filtragens a partir do **nome da usina** (utilizando ``case insensitive``). Caso não **seja passado nenhum valor como parâmetro**
  será retornado **todas as usinas**. Então, caso deseje realizar a filtragem basta adicionar `query params`, de forma que a
  rota fique no formato `/mills?name=usINa` .

<details>
    <summary>Retorno</summary>
    <pre>
        <code>
            [
                {
                    "id": 1,
                    "name": "Usina",
                    "harvests": [
                        {
                            "id": 1,
                            "code": "abc",
                            "start": "2021-01-19",
                            "end": "2021-06-10",
                            "farms": [
                                {
                                    "id": 4,
                                    "code": "as2d0c",
                                    "name": "Fazenda 1",
                                    "fields": [
                                        {
                                            "id": 2,
                                            "code": "mnkjnkjnj",
                                            "geom": {
                                                "type": "Point",
                                                "coordinates": [
                                                    -7.20881888,
                                                    -35.89707436
                                                ]
                                            }
                                        }
                                    ]
                                }
                            ]
                        }
                    ]
                }
            ]
        </code>
    </pre>
</details>

### 🚏 `/mills/{id}`

* **GET**: essa rota é responsável por recuperar uma entidade já cadastrada, caso não seja encontrado é retornado o
  status `404`.

<details>
    <summary>Retorno</summary>
    <pre>
        <code>
            {
                "id": 1,
                "name": "Usina",
                "harvests": []
            }
        </code>
    </pre>
</details>

### 🚏 `/harvests`

* **POST**: rota que permite que uma nova safra seja criada. **Para esta requisição é importante saber que o campo
  millId é responsável por indicar com que usina esta nova safra estará relacionada.** O corpo da requisição é um JSON
  com a seguinte forma:

````json
{
  "code": "abcde",
  "start": "2021-01-19",
  "end": "2021-06-10",
  "millId": 5
}
````

<details>
    <summary>Retorno</summary>
    <pre>
        <code>
            {
                "id": 9,
                "code": "abcde",
                "start": "2021-01-19",
                "end": "2021-06-10",
                "farms": []
            }
        </code>
    </pre>
</details>

* **GET**: rota responsável por listar as safras cadastradas. Ela tem a particularidade de ser filtrada pelas datas de
  início e fim da safra. Caso seja passada **apenas a data de início** será buscado as safras cuja data de início é **maior ou igual** à data passada. Enquanto, se passar **apenas a data de fim** será buscado as safras cuja data de fim
  é **menor ou igual** à data passada. Por fim, **caso seja passada as duas datas** será retornado as safras cuja data
  de início está **entre as duas dadas**. Caso nenhuma data seja passada será retornada todas as safras.

<details>
    <summary>Retorno</summary>
    <pre>
        <code>
            [
                {
                    "id": 8,
                    "code": "ha1kam",
                    "start": "2021-08-19",
                    "end": "2022-01-10",
                    "farms": [],
                    "mill": {
                        "id": 1,
                        "name": "Usina 1"
                    }
                }
            ]
        </code>
    </pre>
</details>

### 🚏 `/harvests/{id}`

* **GET**: essa rota é responsável por recuperar uma entidade já cadastrada, caso não seja encontrado é retornado o
  status `404`.

<details>
    <summary>Retorno</summary>
    <pre>
        <code>
            {
                "id": 8,
                "code": "ha1kam",
                "start": "2021-08-19",
                "end": "2022-01-10",
                "farms": [],
                "mill": {
                    "id": 1,
                    "name": "Usina 1"
                }
            }
        </code>
    </pre>
</details>

### 🚏 `/farms`

* **POST**: rota que permite que uma nova fazenda seja criada. **Para esta requisição é importante saber que o campo
  harvestId é responsável por indicar com que safra esta nova fazenda estará relacionada.** O corpo da requisição é um JSON
  com a seguinte forma:

````json
{
  "code": "xyzab",
  "name": "Fazenda 1",
  "harvestId": 3
}
````

* **GET**: rota responsável por listar todas as fazendas cadastradas, podendo ser filtradas pelo nome e pelo código,
  ambos utilizando `case insentive`. Caso **nenhum** dos dois sejam passados irá ser retornados **todas** as fazendas.

<details>
    <summary>Retorno</summary>
    <pre>
        <code>
            [
                {
                    "id": 5,
                    "code": "lgx98t",
                    "name": "Fazenda 1",
                    "harvest": {
                        "id": 5,
                        "start": "2021-05-10",
                        "end": "2021-10-10",
                        "code": "mszti2"
                    },
                    "fields": [
                        {
                            "id": 2,
                            "code": "mnkjnkjnj",
                            "geom": {
                                "type": "Point",
                                "coordinates": [
                                    -7.20881888,
                                    -35.89707436
                                ]
                            }
                        },
                        {
                            "id": 3,
                            "code": "jnnkjnj",
                            "geom": {
                                "type": "Point",
                                "coordinates": [
                                    -7.21924991,
                                    -35.88119789
                                ]
                            }
                        }
                    ]
                },
            ]
        </code>
    </pre>
</details>

### 🚏 `/farms/{id}`

* **GET**: essa rota é responsável por recuperar uma entidade já cadastrada, caso não seja encontrado é retornado o
  status `404`.

<details>
    <summary>Retorno</summary>
    <pre>
        <code>
            {
                "id": 3,
                "code": "8f6i89",
                "name": "Fazenda 1",
                "harvest": {
                    "id": 1,
                    "start": "2021-05-10",
                    "end": "2021-10-10",
                    "code": "esvv08"
                },
                "fields": []
            }
        </code>
    </pre>
</details>

### 🚏 `/fields`

* **POST**: rota que permite que um novo talhão seja criado. **Para esta requisição é importante saber que o campo
  farmId é responsável por indicar com que fazenda este novo talhão estará relacionada.** O corpo da requisição é um JSON
  com a seguinte forma:

````json
{
  "code": "8q98p9",
  "geom": {
    "type": "Point",
    "coordinates": [-7.1945263, -35.9322058]
  },
  "farmId": 1
}
````

* **GET**: rota responsável por listar todos os talhões cadastrados. Essa rota permite receber um ``query param`` chamado ``code``, onde ele irá realizar a filtragem dos talhões a partir do código cadastrado.

<details>
    <summary>Retorno</summary>
    <pre>
        <code>
            [
                {
                    "id": 1,
                    "code": "6w9cp5",
                    "geom": {
                        "type": "Point",
                        "coordinates": [
                            -35.9322058,
                            -7.1945263
                        ]
                    },
                    "farm": {
                        "id": 2,
                        "code": "1dkcrm",
                        "name": "Fazenda 1"
                    }
                }
            ]
        </code>
    </pre>
</details>

### 🚏 `/fields/{id}`

* **GET**: essa rota é responsável por recuperar uma entidade já cadastrada, caso não seja encontrado é retornado o
  status `404`.

<details>
    <summary>Retorno</summary>
    <pre>
        <code>
            {
                "id": 1,
                "code": "6w9cp5",
                "geom": {
                    "type": "Point",
                    "coordinates": [
                        -35.9322058,
                        -7.1945263
                    ]
                },
                "farm": {
                    "id": 2,
                    "code": "1dkcrm",
                    "name": "Fazenda 1"
                }
            }
        </code>
    </pre>
</details>