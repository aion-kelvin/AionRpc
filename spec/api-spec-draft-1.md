# Aion JSON-RPC API Specification 1.0 [DRAFT]

Last update: 2018-03-18

## 1. Overview

Aion JSON-RPC API is an API for interacting with Aion nodes.  This specification defines the protocol for API; the methods of the API, and error codes used by those methods.  It is based upon [Ethereum's JSON-RPC API](https://github.com/ethereum/wiki/wiki/JSON-RPC), with modifications to accommodate the Aion blockchain.

<span style="color:red">This is a draft.  Editing-related text will be written in red.</span>

## 2. Protocol

### Data structures

Aion JSON-RPC API uses the [JSON-RPC 2.0](https://www.jsonrpc.org/specification), a stateless, light-weight remote procedure call (RPC) protocol.  JSON-RPC defines the data structures for the requests and responses, to which this API adheres.  It is transport agnostic in that the concepts can be used within the same process, over sockets, over http, or in many various message passing environments. It uses [JSON](http://www.json.org/) ([RFC 4627](http://www.ietf.org/rfc/rfc4627.txt)) as data format.

For request, in addition to the four members defined in JSON-RPC 2.0 (`jsonrpc`, `method`, `params`, `id`), the client may optionally include an extra member:

**aion-api**: A String containing the Aion JSON-RPC API version that the request is using.  The server is not required to respect the version, but it should to do so when possible. <span style="color:red">[@chAion]</span>

### Data types and formatting

#### QUANTITY and DATA

In addition to the six data types defined by JSON, there are two key additional datatypes used in Aion JSON-RPC API: quantities and unformatted byte arrays.  Both are passed with a hex encoding represented by a JSON string, but with different requirements to formatting:

When encoding a `QUANTITY` (integers, numbers): encode as hex, prefix with "0x", the most compact representation (slight exception: zero should be represented as "0x0"). Examples:

- 0x41 (65 in decimal)
- 0x400 (1024 in decimal)
- WRONG: 0x (should always have at least one digit - zero is "0x0")
- WRONG: 0x0400 (no leading zeroes allowed)
- WRONG: ff (must be prefixed 0x)

When encoding unformatted `DATA` (byte arrays, account addresses, hashes, bytecode arrays): encode as hex, prefix with "0x", two hex digits per byte. Examples:

- 0x41 (size 1, "A")
- 0x004200 (size 3, "\0B\0")
- 0x (size 0, "")
- WRONG: 0xf0f0f (must be even number of digits)
- WRONG: 004200 (must be prefixed 0x)

#### The default block parameter

The following methods have an extra default block parameter:

- [eth_getBalance](#eth_getbalance)
- [eth_getCode](#eth_getcode)
- [eth_getTransactionCount](#eth_gettransactioncount)
- [eth_getStorageAt](#eth_getstorageat)
- [eth_call](#eth_call)

When requests are made that act on the state of an Aion blockchain, the default block parameter determines the height of the block.  This parameter uses the data type, `TAG`.  The following values are possible for this type:

- `String "earliest"` for the earliest/genesis block
- `String "latest"` - for the latest mined block
- `String "pending"` - for the pending state/transactions

## 3. API methods

The methods for Aion JSON-RPC API are organized into *API modules*:

Core:

- eth:
- net:
- web3:

Non-core:

- personal
- stratum
- ops
- priv
- <span style="color:red"></span>


## eth module

[PENDING] => waiting for sign-off from chaion
[EXAMPLE] => example
[FOLLOWUP] => need more info/analysis
[OK] => done

### eth_accounts

Returns a list of addresses owned by kernel.

#### Parameters

none

#### Returns

`Array of DATA`, 32 Bytes - addresses owned by the kernel.

#### Example

##### Request

        '{"jsonrpc":"2.0","method":"eth_accounts","params":[],"id":1}'

##### Response

        {
          "result": [
            "0xa04432c1b17b4094b0b0da92347dbe3c4c3a972530d9de99e2fd18b05d706a01",
            "0xa0bac18b812332acfd9b25dd489cf82115c3abbae2934cbf1a70a6896755ec54"
          ],
          "id": 1,
          "jsonrpc": "2.0"
        }

***

### eth_blockNumber

Returns the number of most recent block.

#### Parameters

none

#### Returns

`QUANTITY` - integer of the current block number the client is on.

#### Example

##### Request

        '{"jsonrpc":"2.0","method":"eth_blockNumber","params":[],"id":1}'

#### Response

        {
          "result": 2714561,
          "id": 1,
          "jsonrpc": "2.0"
        }

***

### eth_call

Executes a new message call immediately without creating a transaction on the block chain.

#### Parameters

1. `Object` - The transaction call object
  - `from`: `DATA`, 32 Bytes - (optional) The address the transaction is sent from.
  - `to`: `DATA`, 32 Bytes  - The address the transaction is directed to.
  - `gas`: `QUANTITY`  - (optional) Integer of the energy provided for the transaction execution. eth_call consumes zero energy, but this parameter may be needed by some executions.
  - `gasPrice`: `QUANTITY`  - (optional) Integer of the gasPrice used for each paid energy
  - `value`: `QUANTITY`  - (optional) Integer of the value sent with this transaction
  - `data`: `DATA`  - (optional) Hash of the method signature and encoded parameters. For details see [Ethereum Contract ABI](https://github.com/ethereum/wiki/wiki/Ethereum-Contract-ABI) //TODO
2. `QUANTITY|TAG` - (optional) integer block number, or the string `"latest"`, `"earliest"` or `"pending"`, see the [default block parameter](#the-default-block-parameter) //TODO

#### Returns

`DATA` - the return value of executed contract.

#### Example
##### Request

        {
          "jsonrpc": "2.0",
          "method": "eth_call",
          "params": [
            {
              "from": "0xa0f2d72200bf3271725d272ff3fa5a4ac6dc576854e367e6f39a0fd32e2d962f",
              "to": "0xa03684c89bce58a355041ca0f0da8096fd9f38df0109d2003a646f822d25a03f",
              "gas": "0xd431",
              "data": "0xa87d942c"
            }
          ],
          "id": 1
        }

#### Response

        {
          "result": "0x000000000000000000000000000000fe",
          "id": 1,
          "jsonrpc": "2.0"
        }

***

### eth_coinbase

Returns the client coinbase address.

#### Parameters
none

#### Returns

`DATA`, 32 bytes - the current coinbase address.

#### Example
##### Request

        {
          "jsonrpc": "2.0",
          "method": "eth_coinbase",
          "params": [],
          "id": 1
        }
#### Response

        {
          "result": "0xa0f2d72200bf3271725d272ff3fa5a4ac6dc576854e367e6f39a0fd32e2d962f",
          "id": 1,
          "jsonrpc": "2.0"
        }

***

### eth_compileSolidity

Returns compiled solidity code.

##### Parameters

1. `String` - The source code.

#### Returns

`DATA` - The compiled source code.

#### Example
##### Request

        {
          "jsonrpc": "2.0",
          "method": "eth_compileSolidity",
          "params": ["contract test { function multiply(uint a) returns(uint d) {   return a * 7;   } }"],
          "id": 1
        }
#### Response

        {
          "result": {
            "test": {
              "code": "0x605060405234156100105760006000fd5b610015565b60a3806100236000396000f30060506040526000356c01000000000000000000000000900463ffffffff16806390a1703f14603157602b565b60006000fd5b3415603c5760006000fd5b605060048080359060100190919050506066565b6040518082815260100191505060405180910390f35b60006007820290506072565b9190505600a165627a7a72305820a1f5bcb63660014f2677e1b038aba97bb1e515e52f77923115e4b5a11c1c24d80029",
              "info": {
                "abiDefinition": [
                  {
                    "outputs": [
                      {
                        "name": "d",
                        "type": "uint128"
                      }
                    ],
                    "constant": false,
                    "payable": false,
                    "inputs": [
                      {
                        "name": "a",
                        "type": "uint128"
                      }
                    ],
                    "name": "multiply",
                    "type": "function"
                  }
                ],
                "languageVersion": "0",
                "language": "Solidity",
                "compilerVersion": "0.4.15+commit.ecf81ee5.Linux.g++",
                "source": "contract test { function multiply(uint a) returns(uint d) {   return a * 7;   } }"
              }
            }
          },
          "id": 1,
          "jsonrpc": "2.0"
        }

***

### eth_estimateGas

Generates and returns an estimate of how much energy is necessary to allow the transaction to complete. The transaction will not be added to the blockchain. Note that the estimate may be significantly more than the amount of energy actually used by the transaction, for a variety of reasons including VM mechanics and node performance.

#### Parameters

See [eth_call](#eth_call) parameters, expect that all properties are optional. If no gas limit is specified geth uses the block gas limit from the pending block as an upper bound. As a result the returned estimate might not be enough to executed the call/transaction when the amount of gas is higher than the pending block gas limit.

#### Returns

`QUANTITY` - the amount of gas used.

#### Example

##### Request

        {
          "jsonrpc": "2.0",
          "method": "eth_estimateGas",
          "params": [
            {
              "to": "0xa03684c89bce58a355041ca0f0da8096fd9f38df0109d2003a646f822d25a03f",
              "data": "0xa87d942c"
            }
          ],
          "id": 1
        }

#### Response

        {
          "result": "0x572e",
          "id": 1,
          "jsonrpc": "2.0"
        }

***

### eth_gasPrice

Returns the current price per gas in nAmps.

##### Parameters
none

##### Returns

`QUANTITY` - integer of the current gas price in nAmps.

#### Examples

##### Request

        {"method":"eth_gasPrice","params":[],"id":"1","jsonrpc":"2.0"}

##### Response

        {
          "result": "0x2540be400",
          "id": 1,
          "jsonrpc": "2.0"
        }

***

### eth_getBalance [FOLLOWUP - latest/earliest/pending]

Returns the balance of the account of given address.

#### Parameters

1. `DATA`, 32 bytes - adddress to check for balance.
1. `QUANTITY|TAG` - (optional) integer block number, or the string "latest", "earliest" or "pending", see the default block parameter.

#### Returns

`QUANTITY` - integer of the current balane in nanoAmps (nAmp).

#### Examples

##### Request

        {"method":"eth_getBalance","params":["0xa0f37e8c51b4677a4925f6ba623319ca45262b567e98104af5879b7a88c2f25b"],"id":"1","jsonrpc":"2.0"}

##### Response

        {"result":"0x33f8f8b675f988e5","id":"1","jsonrpc":"2.0"}

***

### eth_getBlockByHash

Returns information about a block by hash.

#### Parameters

1. `DATA`, 32 bytes - Hash of a block
1. `Boolean` - (optional) If `true` it returns the full transaction objects, if `false` only the hashes of the transactions.  If not provided, assumed to be `false`.

#### Returns

`Object` - A block object, or null when no block was found:

  - `number`: `QUANTITY` - the block number. `null` when its pending block.
  - `hash`: `DATA`, 32 Bytes - hash of the block. `null` when its pending block.
  - `parentHash`: `DATA`, 32 Bytes - hash of the parent block.
  - `nonce`: `DATA`, 32 Bytes - nonce of the generated proof-of-work. `null` when its pending block.
  - `logsBloom`: `DATA`, 256 Bytes - the bloom filter for the logs of the block. `null` when its pending block.
  - `transactionsRoot`: `DATA`, 32 Bytes - the root of the transaction trie of the block.
  - `stateRoot`: `DATA`, 32 Bytes - the root of the final state trie of the block.
  - `receiptsRoot`: `DATA`, 32 Bytes - the root of the receipts trie of the block.
  - `miner`: `DATA`, 32 Bytes - the address of the beneficiary to whom the mining rewards were given.
  - `difficulty`: `QUANTITY` - integer of the difficulty for this block.
  - `totalDifficulty`: `QUANTITY` - integer of the total difficulty of the chain until this block.
  - `extraData`: `DATA` - the "extra data" field of this block.
  - `size`: `QUANTITY` - integer the size of this block in bytes.
  - `nrgLimit`: `QUANTITY` - the maximum energy allowed in this block in nAmps.
  - `nrgUsed`: `QUANTITY` - the total used energy by all transactions in this block in nAmps.
  - `gasLimit`: `QUANTITY` - same as `nrgLimit`; duplicated for Ethereum-compatibility purposes.
  - `gasUsed`: `QUANTITY` - same as `nrgUsed`; duplicated for Ethereum-compatibility purposes.
  - `timestamp`: `QUANTITY` - the unix timestamp for when the block was collated, in seconds.
  - `transactions`: `Array` - Array of transaction objects, or 32 Bytes transaction hashes depending on the last given parameter.
  - `solution`: `DATA`, 1408 bytes - solution for the generated proof-of-work.

#### Examples

##### Request

        {"method":"eth_getBlockByHash","params":["0x8f8b3dd16c6c972d0910af4c50e13b4521966ff6d9909922bc1e366461b4fe52", true],"id":"1","jsonrpc":"2.0"}

##### Response


        {
          "result": {
            "number": "0x28cce9",
            "hash": "0x8f8b3dd16c6c972d0910af4c50e13b4521966ff6d9909922bc1e366461b4fe52",
            "parentHash": "0xc1e7987aedf107c81b663ce7d092251dddca6c5e2c37e07e2d937b7db0cc6108",
            "nonce": "0x000000000000000000000000c001b43b00000000000000006437700000000000",
            "logsBloom": "0x00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000800000000000000000000000000000000001000000000000000000000000000000000000000000000002000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000440000000000000000000000000000000000000000000000000000000000000001000000000000000",
            "transactionsRoot": "0xcf105253fc2f325a908abd66cfb200f3dfb58f401c58751e64186af97d5202ac",
            "stateRoot": "0x1dd00fa0fd6b1635e54dbee33d5ce19454bfb948d5027f5a989dfb4cd46fee99",
            "receiptsRoot": "0x9417d2515d3656d3aaf316506bf8afcb740759de5d23cd6538ba8269486d88aa",
            "miner": "0xa08091ab0325e384ac45e560d2f85e4b741363aa98881d52d54233a02b33fcaa",
            "difficulty": "0x8a4214",
            "totalDifficulty": "0x1d598faf9420",
            "extraData": "0x4c55584f52000000000000000000000000000000000000000000000000000000",
            "size": "0x84a",
            "nrgLimit": "0xe4b058",
            "nrgUsed": "0x7c1e",
            "gasLimit": "0xe4b058",
            "gasUsed": "0x7c1e",
            "timestamp": "0x5c8c04f9"
            "transactions": [
              { //TODO: should i truncate this? also, some of these are wrong
                "nrgPrice": "0x4a817c800",
                "nrg": 31774,
                "transactionIndex": 0,
                "nonce": 3771,
                "input": "0x4178462f00000000000000000000000000000021",
                "blockNumber": 2673897,
                "gas": 31774,
                "from": "0xa0211089e5a24c2af034b3f71b9149833a39814c13b75f06e1e487faec479c63",
                "to": "0xa03cc62caf592513a6e0626c0d7631c66ea2430c15a18dc43a51a65dcb359da0",
                "value": "0x",
                "hash": "0x8571465914fca68c39dfd127a3a429856fccc9e121ccfc276ced4f95f3331831",
                "gasPrice": "0x4a817c800",
                "timestamp": 1552680185 // TODO wrong timestamp
              }
            ],
            "solution": "0x006521b29b... ", /* truncated for readability */
          },
          "id": "1",
          "jsonrpc": "2.0"
        }


***

### eth_getBlockByNumber

Returns information about a block by block number.

#### Parameters

1. `QUANTITY|TAG`, integer of a block number, or the string "earliest", "latest" or "pending", as in the default block parameter. //TODO formatting
1. `Boolean` - (optional) If `true` it returns the full transaction objects, if `false` only the hashes of the transactions.  If not provided, assumed to be `false`.

#### Returns

See eth_getBlockByHash //TODO formatting

#### Examples

##### Request

        {"method":"eth_getBlockByNumber","params":["0x28cce9", "false"],"id":"1","jsonrpc":"2.0"}

##### Response

See eth_getBlockByHash //TODO formatting

***

### eth_getBlockTransactionCountByHash

Returns the number of transactions in a block from a block matching the given block hash.


#### Parameters

1. `DATA`, 32 Bytes - hash of a block.

#### Returns

`QUANTITY` - integer of the number of transactions in this block.

#### Example

##### Request

        {
          "jsonrpc": "2.0",
          "method": "eth_getBlockTransactionCountByHash",
          "params": [
            "0x49dc23204e4b0afcc0c43461777d13b67fbb77979d98c7f637adfed9086fb465"
          ]
        }

##### Response

        {
          "id":1,
          "jsonrpc": "2.0",
          "result": "0xc"
        }

***

### eth_getBlockTransactionCountByNumber

Returns the number of transactions in a block matching the given block number.

#### Parameters

1. `QUANTITY|TAG` - integer of a block number, or the string `"earliest"`, `"latest"` or `"pending"`, as in the [default block parameter](#the-default-block-parameter). //TODO

#### Returns

`QUANTITY` - integer of the number of transactions in this block.

#### Example

##### Request

        {
          "jsonrpc": "2.0",
          "method": "eth_getBlockTransactionCountByNumber",
          "params": [
            "0x6d39e"
          ]
        }

##### Response

        {
          "id":1,
          "jsonrpc": "2.0",
          "result": "0xc"
        }

***

### eth_getCode

Returns code at a given address.


#### Parameters

1. `DATA`, 32 bytes - address.
2. `QUANTITY|TAG` - (optional) integer block number, or the string `"latest"`, `"earliest"` or `"pending"`, see the [default block parameter](#the-default-block-parameter). //TODO

#### Returns

`DATA` - the code from the given address.

#### Examples

##### Request

        {"jsonrpc":"2.0","method":"eth_getCode","params":["0xa03684c89bce58a355041ca0f0da8096fd9f38df0109d2003a646f822d25a03f", "latest"],"id":1}

##### Response

        {
          "result": "0x60506040526000356c..." /* result truncated for readability */,
          "id": 1,
          "jsonrpc": "2.0"
        }

***

### eth_getCompilers

Returns a list of available compilers in the client.

#### Parameters

none

#### Returns

`Array` - Array of available compilers.

#### Examples

##### Request

        {"jsonrpc":"2.0","method":"eth_getCompilers","params":[],"id":1}

##### Response

        {
          "result": [
            "solidity"
          ],
          "id": 1,
          "jsonrpc": "2.0"
        }

***

### eth_getFilterChanges

Polling method for a filter, which returns an array of logs which occurred since last poll.

#### Parameters

1. `QUANTITY` - the filter id.

#### Returns

`Array` - Array of log objects, or an empty array if nothing has changed since last poll.

- For filters created with `eth_newBlockFilter` the return are block hashes (`DATA`, 32 Bytes), e.g. `["0x3454645634534..."]`.
- For filters created with `eth_newPendingTransactionFilter ` the return are transaction hashes (`DATA`, 32 Bytes), e.g. `["0x6345343454645..."]`.
- For filters created with `eth_newFilter` logs are objects with following params:

  - `removed`: `TAG` - `true` when the log was removed, due to a chain reorganization. `false` if its a valid log.
  - `logIndex`: `QUANTITY` - integer of the log index position in the block. `null` when its pending log.
  - `transactionIndex`: `QUANTITY` - integer of the transactions index position log was created from. `null` when its pending log.
  - `transactionHash`: `DATA`, 32 Bytes - hash of the transactions this log was created from. `null` when its pending log.
  - `blockHash`: `DATA`, 32 Bytes - hash of the block where this log was in. `null` when its pending. `null` when its pending log.
  - `blockNumber`: `QUANTITY` - the block number where this log was in. `null` when its pending. `null` when its pending log.
  - `address`: `DATA`, 32 Bytes - address from which this log originated.
  - `data`: `DATA` - contains the non-indexed arguments of the log.
  - `topics`: `Array of DATA` - Array of 0 to 4 32 Bytes `DATA` of indexed log arguments. (In *solidity*: The first topic is the *hash* of the signature of the event (e.g. `Deposit(address,bytes32,uint256)`), except you declared the event with the `anonymous` specifier.)

#### Example

##### Request

        {
          "jsonrpc": "2.0",
          "method": "eth_getFilterChanges",
          "params": [
            '0x3'
          ],
          "id": 1
        }

##### Response

        {
          "result": [
            {
              "blockHash": "0xf4dccf26fcfc3893b0a38cde4e4694b8c9279e1cd4a94d6eb21f039efd76ccb2",
              "logIndex": "0x0",
              "address": "0xa06092bf447554df44b55531d6fdc08dd2d3eb00be432fc24660579102f30062",
              "removed": false,
              "data": "0x",
              "topics": [
                "0x67eeaf805d3ba71d5d1df73b5f05e3249852980c184ea5611bb188b3b2265d28",
                "0x0000000000000000000000000000000000000000000000000000000000000539",
                "0x23b22795e3b013308903cff5d6637f761a15b7075a6b5e0e167d821e8831a93d"
              ],
              "blockNumber": "0x24b5",
              "transactionIndex": "0x0",
              "transactionHash": "0xfb155778c1be20834a797361f8bc2866661ff3d85d36560bb38f44277065fb74"
            }
          ],
          "id": 1,
          "jsonrpc": "2.0"
        }


***

### eth_getFilterLogs //TODO should we delete this from our spec?

Returns an array of all logs matching filter with given id.

#### Parameters

1. `QUANTITY` - The filter id.

#### Returns

See [eth_getFilterChanges](#eth_getfilterchanges)

##### Request

        {"jsonrpc":"2.0","method":"eth_getFilterLogs","params":["0x16"],"id":74}

##### Response

See [eth_getFilterChanges](#eth_getfilterchanges)

***

### eth_getLogs

Returns an array of all logs matching a given filter object.

#### Parameters

1. `Object` - The filter options:
    - `fromBlock`: `QUANTITY|TAG` - (optional, default: `"latest"`) Integer block number, or `"latest"` for the last mined block or `"pending"`, `"earliest"` for not yet mined transactions.
    - `toBlock`: `QUANTITY|TAG` - (optional, default: `"latest"`) Integer block number, or `"latest"` for the last mined block or `"pending"`, `"earliest"` for not yet mined transactions.
    - `address`: `DATA|Array`, 32 Bytes - (optional) Contract address or a list of addresses from which logs should originate.
    - `topics`: `Array of DATA`,  - (optional) Array of 32 Bytes `DATA` topics. Topics are order-dependent. Each topic can also be an array of DATA with "or" options.

#### Returns

See [eth_getFilterChanges](#eth_getfilterchanges)

#### Example

##### Request

        {
          "jsonrpc": "2.0",
          "method": "eth_getLogs",
          "params": [
            {
              "fromBlock": "0xb10",
              "toBlock": "latest",
              "topics": ["0x67eeaf805d3ba71d5d1df73b5f05e3249852980c184ea5611bb188b3b2265d28"]
            }
          ],
          "id": 1
        }


##### Response

        {
          "result": [
            {
              "blockHash": "0xa7a302a2a8a6e518b623233456779562a5025189eb8dd75efdbeb556ef39fc45",
              "logIndex": "0x0",
              "address": "0xa06092bf447554df44b55531d6fdc08dd2d3eb00be432fc24660579102f30062",
              "removed": false,
              "data": "0x",
              "topics": [
                "0x67eeaf805d3ba71d5d1df73b5f05e3249852980c184ea5611bb188b3b2265d28",
                "0x000000000000000000000000000000000000000000000000000000000000002a",
                "0xe9d225d603ac6b18ff1e21be8cbae33321b9d535e7a6401162a15c72fef25dfe"
              ],
              "blockNumber": "0xb12",
              "transactionIndex": "0x0",
              "transactionHash": "0x09daf72a7a45ecef18c14420082b4dc244eb0c79474f30b07895e9bd057df35e"
            },
            {
              "blockHash": "0x0ff8afa48474fbe9eae4ab3c22a4a45ad84a3e8c0cbb1093879c1b3e74a1488f",
              "logIndex": "0x0",
              "address": "0xa06092bf447554df44b55531d6fdc08dd2d3eb00be432fc24660579102f30062",
              "removed": false,
              "data": "0x",
              "topics": [
                "0x67eeaf805d3ba71d5d1df73b5f05e3249852980c184ea5611bb188b3b2265d28",
                "0x0000000000000000000000000000000000000000000000000000000000000539",
                "0x23b22795e3b013308903cff5d6637f761a15b7075a6b5e0e167d821e8831a93d"
              ],
              "blockNumber": "0x2230",
              "transactionIndex": "0x0",
              "transactionHash": "0x3d1fdf4dd19b50a15d382cb12d6852e98ff1e1e8d9456de4645460d692e2e392"
            }
          ],
          "id": 1,
          "jsonrpc": "2.0"
        }


***

### eth_getStorageAt

Returns the value from a storage position at a given address. 

#### Parameters

1. `DATA`, 20 Bytes - address of the storage.
2. `QUANTITY` - integer of the position in the storage.
3. `QUANTITY|TAG` - integer block number, or the string `"latest"`, `"earliest"` or `"pending"`, see the [default block parameter](#the-default-block-parameter) //TODO

#### Returns

`DATA` - the value at this storage position.

#### Example

Calculating the correct position depends on the storage to retrieve.  The example below is for FVM contracts.

Consider the following contract deployed at `0x49dc23204e4b0afcc0c43461777d13b67fbb77979d98c7f637adfed9086fb465` by address `0xa06e78398580c0f37add71f2b8914bcef5be4938b05e024b96da75438d074ef0`.

        contract Storage {
            uint pos0;
            mapping(address => uint) pos1;
        
            function Storage() {
                pos0 = 1234;
                pos1[msg.sender] = 5678;
            }
        }

Retrieving the value of pos0 is straight forward:


        curl -X POST --data '{"jsonrpc":"2.0", "method": "eth_getStorageAt", "params": ["0x49dc23204e4b0afcc0c43461777d13b67fbb77979d98c7f637adfed9086fb465", "0x0", "latest"], "id": 1}' localhost:8545
        
        {"jsonrpc":"2.0","id":1,"result":"0x00000000000000000000000000000000000000000000000000000000000004d2"}


Retrieving an element of the map is harder. The position of an element in the map is calculated with:

        keccack(LeftPad32(key, 0), LeftPad32(map position, 0))


This means to retrieve the storage on pos1["0xa06e78398580c0f37add71f2b8914bcef5be4938b05e024b96da75438d074ef0"] we need to calculate the position with:

        keccak(decodeHex("000000000000000000000000391694e7e0b0cce554cb130d723a9d27458f9298" + "0000000000000000000000000000000000000000000000000000000000000001"))

The [aion-web3](https://github.com/aionnetwork/aion_web3/) library's console can be used to make the calculation:

        > var key = "000000000000000000000000391694e7e0b0cce554cb130d723a9d27458f9298" + "0000000000000000000000000000000000000000000000000000000000000001"
        undefined
        > web3.sha3(key, {"encoding": "hex"})
        "0x6661e9d6d8b923d5bbaab1b96e1dd51ff6ea2a93520fdc9eb75d059238b8c5e9"

Now to fetch the storage:

        curl -X POST --data '{"jsonrpc":"2.0", "method": "eth_getStorageAt", "params": ["0x49dc23204e4b0afcc0c43461777d13b67fbb77979d98c7f637adfed9086fb465", "0x6661e9d6d8b923d5bbaab1b96e1dd51ff6ea2a93520fdc9eb75d059238b8c5e9", "latest"], "id": 1}' localhost:8545
        
        {"jsonrpc":"2.0","id":1,"result":"0x000000000000000000000000000000000000000000000000000000000000162e"}


***

### eth_getTransactionByBlockHashAndIndex

Returns information about a transaction by block hash and transaction index position.

#### Parameters


1. `DATA`, 32 Bytes - hash of a block.
2. `QUANTITY` - integer of the transaction index position.

#### Returns

See [eth_getTransactionByHash](#eth_gettransactionbyhash)

#### Examples

##### Request

        {"jsonrpc":"2.0","method":"eth_getTransactionByBlockHashAndIndex","params":["0x49dc23204e4b0afcc0c43461777d13b67fbb77979d98c7f637adfed9086fb465", "0x6"],"id":1}

##### Response

see [eth_getTransactionByHash](#eth_gettransactionbyhash)


***

### eth_getTransactionByBlockNumberAndIndex

Returns information about a transaction by block number and transaction index position.

#### Parameters


1. `DATA`, 32 Bytes - hash of a block.
2. `QUANTITY` - integer of the transaction index position.

#### Returns

See [eth_getTransactionByHash](#eth_gettransactionbyhash)

#### Examples

##### Request

        {"jsonrpc":"2.0","method":"eth_getTransactionByBlockNumberAndIndex","params":["0x6d39e", "0x6"],"id":1}

##### Response

see [eth_getTransactionByHash](#eth_gettransactionbyhash)


***

### eth_getTransactionByHash

Returns the information about a transaction requested by transaction hash.

#### Parameters

1. `DATA` - 32 bytes - hash of a transaction

#### Returns

`Object` - A transaction object, or `null` when no transaction was found:

  - `blockHash`: `DATA`, 32 Bytes - hash of the block where this transaction was in. `null` when its pending.
  - `blockNumber`: `QUANTITY` - block number where this transaction was in. `null` when its pending.
  - `from`: `DATA`, 32 Bytes - address of the sender.
  - `nrg`: `QUANTITY` - energy provided by the sender.
  - `nrgPrice`: `QUANTITY` - energy price provided by the sender in nAmps.
  - `gas`: `QUANTITY` - same as `nrg`; duplicated for Ethereum-compatibility purposes.
  - `gasPrice`: `QUANTITY` - same as `nrgPrice`; duplicated for Ethereum-compatibility purposes.
  - `hash`: `DATA`, 32 Bytes - hash of the transaction.
  - `input`: `DATA` - the data of the transaction.
  - `nonce`: `QUANTITY` - the number of transactions made by the sender prior to this one.
  - `to`: `DATA`, 32 Bytes - address of the receiver. `null` when its a contract creation transaction.
  - `transactionIndex`: `QUANTITY` - integer of the transaction's index position in the block. `null` when its pending.
  - `value`: `QUANTITY` - value transferred in nAmps.
  - `timestamp`: `QUANTITY` - the unix timestamp for when the transaction was sent, in seconds.

#### Examples

##### Request

        curl -s -X POST -H"Content-type: application/json"  --data '{"method":"eth_getTransactionByHash","params":["0xfafcae97932003ef1b6a896d51c47b2abb88d97339861dc803d21424dfe0402b"],"id":"1","jsonrpc":"2.0"}' http://localhost:6545

##### Response

        {   
          "result": {
            "blockHash": "0x49dc23204e4b0afcc0c43461777d13b67fbb77979d98c7f637adfed9086fb465",
            "blockNumber": "0x6d39e",
            "from": "0xa06e78398580c0f37add71f2b8914bcef5be4938b05e024b96da75438d074ef0",
            "nrg": "0x7a120",
            "nrgPrice": "0x2540be400",
            "gas": "0x7a120",
            "gasPrice": "0x2540be400",
            "hash": "0xfafcae97932003ef1b6a896d51c47b2abb88d97339861dc803d21424dfe0402b",
            "input": "0x",
            "nonce": "0x1c2b",
            "to": "0xa0a7168688acbd3cf4f44525d689f4d918743290c99f4b2b5c1119bfd41ac98b",
            "transactionIndex": "0x6",
            "value": "0xd4e0885c33cf3a",
            "timestamp": "0x5b2634f8"
          },  
          "id": "1",
          "jsonrpc": "2.0"
        }


***

### eth_getTransactionCount [FOLLOWUP - latest/earliest/pending]

Returns the number of transactions *sent* from an address.

#### Parameters

1. `DATA`, 32 Bytes - address.
2. `QUANTITY|TAG` - (optional) integer block number, or the string `"latest"`, `"earliest"` or `"pending"`, see the [default block parameter](#the-default-block-parameter)

#### Returns

`QUANTITY` - integer of the number of transactions send from this address.

#### Example

##### Request

        {"jsonrpc":"2.0","method":"eth_getTransactionCount","params":["0xa0211089e5a24c2af034b3f71b9149833a39814c13b75f06e1e487faec479c63","latest"],"id":1}

##### Response

        {
          "result": "0xf76",
          "id": 1,
          "jsonrpc": "2.0"
        }

***

### eth_getTransactionReceipt

Returns the receipt of a transaction by transaction hash.

**Note** That the receipt is not available for pending transactions.

#### Parameters

1. `DATA`, 32 Bytes - hash of a transaction

#### Returns

`Object` - A transaction receipt object, or `null` when no receipt was found:

  - `transactionHash `: `DATA`, 32 Bytes - hash of the transaction.
  - `transactionIndex`: `QUANTITY` - integer of the transaction's index position in the block.
  - `blockHash`: `DATA`, 32 Bytes - hash of the block where this transaction was in.
  - `blockNumber`: `QUANTITY` - block number where this transaction was in.
  - `from`: `DATA`, 32 Bytes - address of the sender.
  - `to`: `DATA`, 32 Bytes - address of the receiver. null when it's a contract creation transaction.
  - `cumulativeNrgUsed `: `QUANTITY ` - The total amount of energy used when this transaction was executed in the block.
  - `cumulativeGasUsed `: `QUANTITY ` - same as `cumulativeNrgUsed`; duplicated for Ethereum-compatibility purposes.
  - `nrgUsed `: `QUANTITY ` - The amount of energy used by this specific transaction alone.
  - `gasUsed `: `QUANTITY ` - same as `nrgUsed`; duplicated for Ethereum-compatibility purposes.
  - `nrgLimit `: `QUANTITY ` - The energy limit for the transaction
  - `gasLimit `: `QUANTITY ` - same as `nrgLimit`; duplicated for Ethereum-compatibility purposes.
  -
  - `contractAddress `: `DATA`, 20 Bytes - The contract address created, if the transaction was a contract creation, otherwise `null`.
  - `logs`: `Array` - Array of log objects, which this transaction generated.
  - `logsBloom`: `DATA`, 256 Bytes - Bloom filter for light clients to quickly retrieve related logs.  
  - `root` : `DATA` 32 bytes of post-transaction stateroot if it was successful //TODO check against impls
  - `status`: `QUANTITY` either `1` (success) or `0` (failure)

#### Example

##### Request

        {"jsonrpc":"2.0","method":"eth_getTransactionReceipt","params":["0x8571465914fca68c39dfd127a3a429856fccc9e121ccfc276ced4f95f3331831"],"id":1}

##### Response

        {
          "result": {
            "transactionHash": "0x8571465914fca68c39dfd127a3a429856fccc9e121ccfc276ced4f95f3331831",
            "transactionIndex": "0x0",
            "blockHash": "0x8f8b3dd16c6c972d0910af4c50e13b4521966ff6d9909922bc1e366461b4fe52",
            "blockNumber": "0x28cce9",
            "from": "0xa0211089e5a24c2af034b3f71b9149833a39814c13b75f06e1e487faec479c63",
            "to": "0xa03cc62caf592513a6e0626c0d7631c66ea2430c15a18dc43a51a65dcb359da0",
            "cumulativeNrgUsed": "0x7c1e",
            "cumulativeGasUsed": "0x7c1e",
            "nrgUsed": "0x7c1e",
            "gasUsed": "0x7c1e",
            "nrgPrice": "0x04a817c800",
            "gasPrice": "0x04a817c800",
            "nrgLimit": "0x7c1e",
            "gasLimit": "0x7c1e",
            "contractAddress": null,
            "logs": [
              {   
                "address": "0xa03cc62caf592513a6e0626c0d7631c66ea2430c15a18dc43a51a65dcb359da0",
                "logIndex": "0x0",
                "data": "0x00000000000000000000000000000021",
                "topics": [
                  "0x8d58ad5a07d0080c710a21010568c7fd3ccbefcb02a313581024843b98e03513"
                ],  
                "blockNumber": "0x28cce9",
                "transactionIndex": "0x0"
              }   
            ],  
            "logsBloom": "00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000800000000000000000000000000000000001000000000000000000000000000000000000000000000002000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000440000000000000000000000000000000000000000000000000000000000000001000000000000000",            
            "root": "9417d2515d3656d3aaf316506bf8afcb740759de5d23cd6538ba8269486d88aa",
            "status": "0x1"
          },  

          "id": 1,
          "jsonrpc": "2.0"
        }   

***

### eth_hashrate

Returns the number of hashes per second that the node is mining with.

#### Parameters
none

#### Returns

`QUANTITY` - number of hashes per second.

#### Example

##### Request

        {"jsonrpc":"2.0","method":"eth_hashrate","params":[],"id":1}

##### Response

        { "id":1, "jsonrpc": "2.0", "result": "0x38a" }

***

### eth_mining

Returns `true` if node is actively mining new blocks.

#### Parameters
none

#### Returns

`Boolean` - returns `true` of the client is mining, otherwise `false`.

#### Example

##### Request

        {"jsonrpc":"2.0","method":"eth_mining","params":[],"id":1}

##### Response

        { "id":1, "jsonrpc": "2.0", "result": false }

***

### eth_newBlockFilter

Creates a filter in the node, to notify when a new block arrives.
To check if the state has changed, call [eth_getFilterChanges](#eth_getfilterchanges).

#### Parameters

None

#### Returns

`QUANTITY` - A filter id.

#### Example

##### Request

        {
          "jsonrpc": "2.0",
          "method": "eth_newBlockFilter",
          "params": [],
          "id": 1
        }

##### Response

        {
          "result": "0x4",
          "id": 1,
          "jsonrpc": "2.0"
        }

***

### eth_newFilter

Creates a filter object, based on filter options, to notify when the state changes (logs).
To check if the state has changed, call [eth_getFilterChanges](#eth_getfilterchanges).

#### A note on specifying topic filters:

Topics are order-dependent. A transaction with a log with topics [A, B] will be matched by the following topic filters:
* `[]` "anything"
* `[A]` "A in first position (and anything after)"
* `[null, B]` "anything in first position AND B in second position (and anything after)"
* `[A, B]` "A in first position AND B in second position (and anything after)"
* `[[A, B], [A, B]]` "(A OR B) in first position AND (A OR B) in second position (and anything after)"

#### Parameters

1. `Object` - The filter options:
  - `fromBlock`: `QUANTITY|TAG` - (optional, default: `"latest"`) Integer block number, or `"latest"` for the last mined block or `"pending"`, `"earliest"` for not yet mined transactions.
  - `toBlock`: `QUANTITY|TAG` - (optional, default: `"latest"`) Integer block number, or `"latest"` for the last mined block or `"pending"`, `"earliest"` for not yet mined transactions.
  - `address`: `DATA|Array`, 20 Bytes - (optional) Contract address or a list of addresses from which logs should originate.
  - `topics`: `Array of DATA`,  - (optional) Array of 32 Bytes `DATA` topics. Topics are order-dependent. Each topic can also be an array of DATA with "or" options.

##### Returns

`QUANTITY` - The newly created filter's id.

#### Example

##### Request

        {
          "jsonrpc": "2.0",
          "method": "eth_newFilter",
          "params": [
            {
              "topics": ["0x67eeaf805d3ba71d5d1df73b5f05e3249852980c184ea5611bb188b3b2265d28"]
            }
          ],
          "id": 1
        }

##### Response

        {
          "result": "0x5",
          "id": 1,
          "jsonrpc": "2.0"
        }
***

### eth_newPendingTransactionFilter

Creates a filter in the node, to notify when new pending transactions arrive.
To check if the state has changed, call [eth_getFilterChanges](#eth_getfilterchanges).

##### Parameters
None

##### Returns

`QUANTITY` - A filter id.

#### Example

##### Request

        {
          "jsonrpc": "2.0",
          "method": "eth_newPendingTransactionFilter",
          "params": [],
          "id": 1
        }

##### Response

        {
          "result": "0x4",
          "id": 1,
          "jsonrpc": "2.0"
        }

***

### eth_protocolVersion

Returns the Aion network P2p protocol version of the kernel

#### Parameters
none

#### Returns

`String` - the Aion network P2p protocol version of the kernel

#### Example

##### Request 

        {"jsonrpc":"2.0","method":"eth_protocolVersion","params":[],"id":1}

##### Response

        {
          "result": "0x0",
          "id": 1,
          "jsonrpc": "2.0"
        }

***

### eth_sendRawTransaction

Creates new message call transaction or a contract creation for signed transactions.

#### Parameters

1. `DATA`, The signed transaction data.

##### Returns

`DATA`, 32 Bytes - the transaction hash, or the zero hash if the transaction is not yet available.

Use [eth_getTransactionReceipt](#eth_gettransactionreceipt) to get the contract address, after the transaction was mined, when you created a contract.

#### Example

##### Request 

        {
          "jsonrpc": "2.0",
          "method": "eth_sendRawTransaction",
          "params": [
            "0xf89c0ba0a06092bf447554df44b55531d6fdc08dd2d3eb00be432fc24660579102f300620a80870584b388195418830186a08800000002540be40001b860baa9780538faa8beae8b51968ef34922086135bc5a442a7187a5924828ba568efcaa4d2ee4643424c3c2890fbfa9a92d97aedcac62ac860fc3bd02be7aebc50402d9b38c98dc9ccc77660ea8dae3f3a14aa1fcddeb3d282e63d58ad3382c7002"
          ],
          "id": 1
        }

##### Request 

        {
          "result": "0x99207e6fe9b3a728c28779410014ce5b7e1a4864fd6590f8cd3a423ecc827f93",
          "id": 1,
          "jsonrpc": "2.0"
        }

***

### eth_sendTransaction [FOLLOWUP]

Creates new message call transaction or a contract creation, if the data field contains code.

//TODO this requires unlockAccount, which isn't actually part of the core spec...

#### Parameters

1. `Object` - The transaction object
  - `from`: `DATA`, 32 Bytes - The address the transaction is sent from.
  - `to`: `DATA`, 32 Bytes - (empty when creating new contract) The address the transaction is directed to.
  - `gas`: `QUANTITY`  - (optional; default for contract creation: 350000; for other transactions: 90000) Integer of the energy provided for the transaction execution. It will return unused energy.
  - `gasPrice`: `QUANTITY`  - (optional, default: the value given by [eth_gasPrice](#eth_gasPrice)) Integer of the gasPrice used for each paid energy
  - `value`: `QUANTITY`  - (optional) Integer of the value sent with this transaction
  - `data`: `DATA`  - (optional) Empty for pure balance transfers; otherwise, the encoding of a contract for deployment or a contract method call.  For deatils, see http://abc //TODO
  - `nonce`: `QUANTITY`  - (optional) Integer of a nonce. This allows to overwrite your own pending transactions that use the same nonce.
  -  `type`: `DATA` - (optional; defaults to `0x00`) Type code of transaction. For details, see http://abc //TODO

#### Returns

`DATA`, 32 Bytes - the transaction hash, or the zero hash if the transaction is not yet available.

Use [eth_getTransactionReceipt](#eth_gettransactionreceipt) to get the contract address, after the transaction was mined, when you created a contract.

#### Example

##### Request

        {
          "jsonrpc": "2.0",
          "method": "eth_sendTransaction",
          "params": [
            {
              "from": "0xa0f2d72200bf3271725d272ff3fa5a4ac6dc576854e367e6f39a0fd32e2d962f",
              "to": "0xa06092bf447554df44b55531d6fdc08dd2d3eb00be432fc24660579102f30062",
              "value": "0xa",
              "gasPrice": "0x2540be400"
            }
          ],
          "id": 1
        }

##### Response

        {
          "result": "0xb26476163dcd7c597939597c1bc330cc10a5158b121dfb176cb6bcc7dfc9622f",
          "id": 1,
          "jsonrpc": "2.0"
        }

***

### eth_sign [FOLLOWUP]

The sign method calculates an Aion-specific signature with: `sign(keccak256("\x15Aion Signed Message:\n" + len(message) + message)))`.

By adding a prefix to the message makes the calculated signature recognisable as an Ethereum specific signature. This prevents misuse where a malicious DApp can sign arbitrary data (e.g. transaction) and use the signature to impersonate the victim.

**Note** the address to sign with must be unlocked.  //TODO

#### Parameters

1. `DATA`, 32 bytes - address.
2. `DATA`, N bytes - message to sign.

#### Returns

`DATA`: Signature

#### Example

##### Request

        {
          "jsonrpc": "2.0",
          "method": "eth_sign",
          "params": [
            "0xa0f2d72200bf3271725d272ff3fa5a4ac6dc576854e367e6f39a0fd32e2d962f",
            "0xdeadbeef"
          ],
          "id": 1
        }

##### Response

        {
          "result": "0x09b7f34fe24212e57e9c11f79627a58902b5ebadb7dd18b8d885104ddc818fb5312c32a6211aa5867e6403cb90ea1a1e564d470f203d370a36f347187da6fe00",
          "id": 1,
          "jsonrpc": "2.0"
        }

***

### eth_submitHashrate

Used for submitting mining hashrate.


#### Parameters

1. `QUANTITY` - The filter id.
2. `DATA` - A hexadecimal(32 bytes) ID identifying the client

#### Returns

`Boolean` - returns `true` if submitting went through succesfully and `false` otherwise.

#### Example

##### Request

        {
          "jsonrpc": "2.0",
          "method": "eth_submitHashrate",
          "params": [
            "0x1f",
            "0x59daa26581d0acd1fce254fb7e85952f4c09d0915afd33d3886cd914bc7d283c"
          ],
          "id": 1
        }

##### Response

        {
          "id":1,
          "jsonrpc":"2.0",
          "result": true
        }

***

### eth_syncing

Returns an object with data about the sync status or `false`.


#### Parameters

none

#### Returns

`Object|Boolean`, An object with sync status data or `FALSE`, when not syncing:
  - `startingBlock`: `QUANTITY` - The block at which the import started (will only be reset, after the sync reached his head)
  - `currentBlock`: `QUANTITY` - The current block, same as eth_blockNumber
  - `highestBlock`: `QUANTITY` - The estimated highest block of the network (0 if no peers)

#### Example

##### Request

        {
          "jsonrpc": "2.0",
          "method": "eth_syncing",
          "params": [],
          "id": 1
        }

##### Response

        // When syncing
        {
          "id":1,
          "jsonrpc": "2.0",
          "result": {
            startingBlock: '0x384',
            currentBlock: '0x386',
            highestBlock: '0x454'
          }
        }
        
        // Or when not syncing
        {
          "id":1,
          "jsonrpc": "2.0",
          "result": false
        }

***

### eth_uninstallFilter

Uninstalls a filter with given id. Should always be called when watch is no longer needed.
Additonally Filters timeout when they aren't requested with [eth_getFilterChanges](#eth_getfilterchanges) for a period of time.


#### Parameters

1. `QUANTITY` - The filter id.

#### Returns

`Boolean` - `true` if the filter was successfully uninstalled, otherwise `false`.

#### Example 

##### Request 

        {
          "jsonrpc": "2.0",
          "method": "eth_uninstallFilter",
          "params": [
            "0x0"
          ],
          "id": 1
        }

##### Response

        {
          "result": false,
          "id": 1,
          "jsonrpc": "2.0"
        }

### eth_signTransaction [TODO]

Signs a transaction object (but does not send it).

//TODO this requires unlockAccount, which isn't actually part of the core spec...

#### Parameters

1. `Object` - The transaction object (see [eth_sendTransaction](#eth_sendTransaction))

#### Returns

`Object`, the transaction object and raw signed transaction

  - `tx`: `Object` - The transaction that was signed (missing optional parameters are automatically populated by the kernel and those values are shown here). See parameters of [eth_sendTransaction](#eth_sendTransaction) for details on the transaction object.
  - `raw`: `DATA` - The raw RLP-encoded signed transaction.  Can be passed to [eth_sendRawTransaction](#eth_sendRawTransaction).

#### Example

##### Request

        {
          "jsonrpc": "2.0",
          "method": "eth_signTransaction",
          "params": [
            {
              "from": "0xa0f2d72200bf3271725d272ff3fa5a4ac6dc576854e367e6f39a0fd32e2d962f",
              "to": "0xa06092bf447554df44b55531d6fdc08dd2d3eb00be432fc24660579102f30062",
              "value": "0xa",
              "gas": "0xea60",
              "gasPrice": "0x2540be400"
            },
            "0xa0f2d72200bf3271725d272ff3fa5a4ac6dc576854e367e6f39a0fd32e2d962f"
          ],
          "id": 1
        }

##### Response

        {
          "result": {
            "tx": {
              "nrgPrice": "0x2540be400",
              "data": "0x",
              "nrg": "0xea60",
              "gas": "0xea60",
              "to": "0xa06092bf447554df44b55531d6fdc08dd2d3eb00be432fc24660579102f30062",
              "nonce": "0x0d",
              "value": "0x0a",
              "hash": "0x3ad98b558a9b59f00c2326d5027dc473fea23c773a4a5b0a374bc7c6d8b43f1e",
              "gasPrice": "0x2540be400"
            },
            "raw": "0xf89c0da0a06092bf447554df44b55531d6fdc08dd2d3eb00be432fc24660579102f300620a8088000584f12f9c168882ea608800000002540be40001b860baa9780538faa8beae8b51968ef34922086135bc5a442a7187a5924828ba568e3af7c88b14f6209567eb2cd3ffaeb877327becf8722a13452c00469e0a5effd6ca87652036a03d6dd6a0c36581746946bc2318bf9334f2dd1cae5c4e26557b0b"
          },
          "id": 1,
          "jsonrpc": "2.0"
        }

***

## net module

### net_version

Returns the current network id.

#### Parameters

none

#### Returns

`String` - The current network id. //TODO should we just make this hex?
- `"1"`: Aion Mainnet
- `"32"`: Mastery Testnet
- `"31"`: avmtestnet Testnet

#### Example

##### Request

        {
          "jsonrpc": "2.0",
          "method": "net_version",
          "params": [],
          "id": 1
        }

##### Response

        {
          "result": "32",
          "id": 1,
          "jsonrpc": "2.0"
        }

***

### net_listening

Returns `true` if client is actively listening for network connections.

#### Parameters

none

#### Returns

`Boolean` - `true` when listening, otherwise `false`.

#### Example

##### Request

        {
          "jsonrpc": "2.0",
          "method": "net_listening",
          "params": [],
          "id": 1
        }

##### Response

        {
          "result": true,
          "id": 1,
          "jsonrpc": "2.0"
        }

***

### net_peerCount

Returns number of peers currently connected to the client.

#### Parameters

none

#### Returns

`QUANTITY` - integer of the number of connected peers.

#### Example

##### Request

        {
          "jsonrpc": "2.0",
          "method": "net_peerCount",
          "params": [],
          "id": 1
        }

##### Response

        {
          "id":1,
          "jsonrpc": "2.0",
          "result": "0x2"
        }

***

## web3 module

### web3_clientVersion

Returns the current kernel version.

#### Parameters

none

#### Returns

`QUANTITY` - integer of the number of connected peers.

#### Example

##### Request

        {
          "jsonrpc": "2.0",
          "method": "web3_clientVersion",
          "params": [],
          "id": 1
        }

##### Response

        {
          "result": "Aion(J)/v0.3.3.fe7a3ea/Linux/Java-11.0.1",
          "id": 1,
          "jsonrpc": "2.0"
        }

***

### web3_sha3

Returns Keccak-256 (*not* the standardized SHA3-256) of the given data.

#### Parameters

1. `DATA` - the data to convert into a SHA3 hash.

#### Returns

`DATA` - The SHA3 result of the given string.

#### Example

##### Request

        {
          "jsonrpc": "2.0",
          "method": "web3_sha3",
          "params": [
            "0x68656c6c6f20776f726c64"
          ],
          "id": 1
        }

##### Response

        {
          "result": "0x47173285a8d7341e5e972fc677286384f802f8ef42a5ec5f03bbfa254cb01fad",
          "id": 1,
          "jsonrpc": "2.0"
        }

## 4. Application error codes

Aion JSON-RPC API inherits the following standard error codes from JSON-RPC 2.0:

| Code    | Possible Return message | Description |
| --------|-------------------------|-------------|
|-32700 | Parse error       | Invalid JSON was received by the server. An error occurred on the server while parsing the JSON text. |
|-32600 | Invalid Request   | The JSON sent is not a valid Request object. |
|-32601 | Method not found  | The method does not exist / is not available. |
|-32602 | Invalid params    | Invalid method parameter(s). |
|-32603 | Internal error    | Internal JSON-RPC error. Should be used as a general error when a more specific error cannot be provided. |

In addition, Aion JSON-RPC API defines the following custom error codes:

| Code    | Possible Return message | Description |
| --------|-------------------------|-------------|
|1 | Unauthorized       | Should be used when some action is not authorized, e.g. sending from a locked account.
|2 | Action not allowed | Should be used when some action is not allowed, for instance, calling an API method that has been disabled in configuration.
|3 | Execution error    | Should be used to when any kind of failure during transaction execution.   |
|4 | Compilation error  | Should be used to indicate Solidity compilation errors.  |

## 5. Appendix

### Omitted Modules from Ethereum JSON-RPC

The following modules exist in Ethereum JSON-RPC, but are **not** in Aion JSON-RPC API:

1. **db** -- Listed as deprecated in Eth spec; can't think of a purposeful use-case for us that requires direct manipulation of the DB through RPC.
1. **shh** -- Aion does not support Whisper, so don't need RPC methods for it

### 'eth_' methods list explanation

To figure out what eth_ methods should be in spec, I made a list of all eth_ methods in our Java impl, Rust impl, and Eth spec.  The table below shows which method is in which spec/impl and whether it should be in this spec.

<div style="color:red">
Won't keep this table around in the final version, but putting it here to organize what methods we have so far and what needs to be added/removed.  The information in this table is complete. <br/><br/>

"aion impl" - means that the method exists in aion java impl v0.3.3 <br/>
"aionr impl" - means that the method exists in aion rust impl (used a list from Miao)<br/>
"Eth spec" - means that it appears in the core Eth spec (does not include other APIs they have, i.e. `personal`.  Source: https://github.com/ethereum/wiki/wiki/JSON-RPC)<br/>
"Aion spec" - whether the new Aion spec should include it.  A method not being included in the spec does not mean that it can't exist in an implementation; but any such implementations should have documentation if it is meant for end-user consumption <br/>
</div>


| method        | aion impl | aionr impl | Eth spec | Aion spec | Notes |
|---------------|-----------|------------|----------|-----------|-------|
|eth_accounts |✓|✓|✓|✓ | |
|eth_blockNumber |✓|✓|✓|✓ | |
|eth_call |✓|✓|✓|✓ | |
|eth_coinbase |✓|✓|✓|✓ | |
|eth_compileSolidity |✓|✓|✓|✓ | |
|eth_estimateGas |✓|✓|✓|✓ | |
|eth_gasPrice |✓|✓|✓|✓ | |
|eth_getBalance |✓|✓|✓|✓ | |
|eth_getBlockByHash |✓|✓|✓|✓ | |
|eth_getBlockByNumber |✓|✓|✓|✓ | |
|eth_getBlockTransactionCountByHash |✓|✓|✓|✓ | |
|eth_getBlockTransactionCountByNumber |✓|✓|✓|✓ | |
|eth_getCode |✓|✓|✓|✓ | |
|eth_getCompilers |✓|✓|✓|✓ | |
|eth_getFilterChanges |✓|✓|✓|✓ | |
|eth_getFilterLogs |✓|✓|✓|✓ | |
|eth_getLogs |✓|✓|✓|✓ | |
|eth_getStorageAt |✓|✓|✓|✓ | |
|eth_getTransactionByBlockHashAndIndex |✓|✓|✓|✓ | |
|eth_getTransactionByBlockNumberAndIndex |✓|✓|✓|✓ | |
|eth_getTransactionByHash |✓|✓|✓|✓ | |
|eth_getTransactionCount |✓|✓|✓|✓ | |
|eth_getTransactionReceipt |✓|✓|✓|✓ | |
|eth_hashrate |✓|✓|✓|✓ | |
|eth_mining |✓|✓|✓|✓ | |
|eth_newBlockFilter |✓|✓|✓|✓ | |
|eth_newFilter |✓|✓|✓|✓ | |
|eth_newPendingTransactionFilter |✓|✓|✓|✓ | |
|eth_protocolVersion |✓|✓|✓|✓ | |
|eth_sendRawTransaction |✓|✓|✓|✓ | |
|eth_sendTransaction |✓|✓|✓|✓ | |
|eth_sign |✓|✓|✓|✓ | |
|eth_submitHashrate |✓|✓|✓|✓ | |
|eth_syncing |✓|✓|✓|✓ | |
|eth_uninstallFilter |✓|✓|✓|✓ | |
|eth_signTransaction |✓|✓|✗|✓ | Introduced in Aion; never existed in Eth |
|eth_compileLLL |✗|✗|✓|✗ |LLL not supported by Aion; see [1] |
|eth_compileSerpent |✗|✗|✓|✗ |Serpent not supported by Aion; see [1] |
|eth_getProof |✗|✗|✓|✗| Was recently added to Eth (see [2]); we should add it too, but not clear how it will look for storage and state proof, so not including in spec for now.
|eth_getUncleByBlockHashAndIndex |✗|✗|✓|✗ |Aion doesn't have 'uncle'; see [1] |
|eth_getUncleByBlockNumberAndIndex |✗|✗|✓|✗ |Aion doesn't have 'uncle'; see [1] |
|eth_getUncleCountByBlockHash |✗|✗|✓|✗ |Aion doesn't have 'uncle'; see [1] |
|eth_getUncleCountByBlockNumber |✗|✗|✓|✗ |Aion doesn't have 'uncle'; see [1] |
|eth_getWork |✗|✓|✓|✗| In aion, fulfilled by getblocktemplate; see [3]
|eth_submitWork |✗|✓|✓|✗| In aion, fulfilled by submitblock; see [3] |  
|eth_subscribe |✗|✓|✗|✗|Not in core Eth spec <span style="color:red">[@chAion]</span>
|eth_unsubscribe |✗|✓|✗|✗ |Not in core Eth spec


#### notes:

[1] https://github.com/aionnetwork/aion/wiki/JSON-RPC-API-Docs#21-unsupported-endpoints

[2] https://github.com/ethereum/EIPs/issues/1186

[3] `getblocktemplate` and `submitblock` are names from BTC (https://en.bitcoin.it/wiki/Original_Bitcoin_client/API_calls_list).  When Aion RPC server was implemented, we were testing against a BTC miner which expected those names.  

### Spec TODOs

- use consistent format for examples
- "latest" "earliest" "pending" -- are they actually implemented in the places where they should be?  does it work for all methods where they're supposed to work?  also need to add a section explaining it in this spec
- getCode - AVM result?
- sanitize examples
