# Aion JSON-RPC API Specification 1.0 [DRAFT]

Last update: 2018-03-18

<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->


- [1. Overview](#1-overview)
- [2. Protocol](#2-protocol)
  - [Data structures](#data-structures)
  - [Data types and formatting](#data-types-and-formatting)
    - [QUANTITY and DATA](#quantity-and-data)
    - [Default block parameter](#default-block-parameter)
- [3. API methods](#3-api-methods)
- [eth module](#eth-module)
  - [eth_accounts](#eth_accounts)
    - [Parameters](#parameters)
    - [Returns](#returns)
    - [Example](#example)
      - [Request](#request)
      - [Response](#response)
  - [eth_blockNumber](#eth_blocknumber)
    - [Parameters](#parameters-1)
    - [Returns](#returns-1)
    - [Example](#example-1)
      - [Request](#request-1)
    - [Response](#response-1)
  - [eth_call](#eth_call)
    - [Parameters](#parameters-2)
    - [Returns](#returns-2)
    - [Example](#example-2)
      - [Request](#request-2)
    - [Response](#response-2)
  - [eth_coinbase](#eth_coinbase)
    - [Parameters](#parameters-3)
    - [Returns](#returns-3)
    - [Example](#example-3)
      - [Request](#request-3)
    - [Response](#response-3)
  - [eth_compileSolidity](#eth_compilesolidity)
      - [Parameters](#parameters-4)
    - [Returns](#returns-4)
    - [Example](#example-4)
      - [Request](#request-4)
    - [Response](#response-4)
  - [eth_estimateGas](#eth_estimategas)
    - [Parameters](#parameters-5)
    - [Returns](#returns-5)
    - [Example](#example-5)
      - [Request](#request-5)
    - [Response](#response-5)
  - [eth_gasPrice](#eth_gasprice)
      - [Parameters](#parameters-6)
      - [Returns](#returns-6)
    - [Examples](#examples)
      - [Request](#request-6)
      - [Response](#response-6)
  - [eth_getBalance](#eth_getbalance)
    - [Parameters](#parameters-7)
    - [Returns](#returns-7)
    - [Examples](#examples-1)
      - [Request](#request-7)
      - [Response](#response-7)
  - [eth_getBlockByHash](#eth_getblockbyhash)
    - [Parameters](#parameters-8)
    - [Returns](#returns-8)
    - [Examples](#examples-2)
      - [Request](#request-8)
      - [Response](#response-8)
  - [eth_getBlockByNumber](#eth_getblockbynumber)
    - [Parameters](#parameters-9)
    - [Returns](#returns-9)
    - [Examples](#examples-3)
      - [Request](#request-9)
      - [Response](#response-9)
  - [eth_getBlockTransactionCountByHash](#eth_getblocktransactioncountbyhash)
    - [Parameters](#parameters-10)
    - [Returns](#returns-10)
    - [Example](#example-6)
      - [Request](#request-10)
      - [Response](#response-10)
  - [eth_getBlockTransactionCountByNumber](#eth_getblocktransactioncountbynumber)
    - [Parameters](#parameters-11)
    - [Returns](#returns-11)
    - [Example](#example-7)
      - [Request](#request-11)
      - [Response](#response-11)
  - [eth_getCode](#eth_getcode)
    - [Parameters](#parameters-12)
    - [Returns](#returns-12)
    - [Examples](#examples-4)
      - [Request](#request-12)
      - [Response](#response-12)
  - [eth_getCompilers](#eth_getcompilers)
    - [Parameters](#parameters-13)
    - [Returns](#returns-13)
    - [Examples](#examples-5)
      - [Request](#request-13)
      - [Response](#response-13)
  - [eth_getFilterChanges](#eth_getfilterchanges)
    - [Parameters](#parameters-14)
    - [Returns](#returns-14)
    - [Example](#example-8)
      - [Request](#request-14)
      - [Response](#response-14)
  - [eth_getFilterLogs](#eth_getfilterlogs)
    - [Parameters](#parameters-15)
    - [Returns](#returns-15)
      - [Request](#request-15)
      - [Response](#response-15)
  - [eth_getLogs](#eth_getlogs)
    - [Parameters](#parameters-16)
    - [Returns](#returns-16)
    - [Example](#example-9)
      - [Request](#request-16)
      - [Response](#response-16)
  - [eth_getStorageAt](#eth_getstorageat)
    - [Parameters](#parameters-17)
    - [Returns](#returns-17)
    - [Example](#example-10)
  - [eth_getTransactionByBlockHashAndIndex](#eth_gettransactionbyblockhashandindex)
    - [Parameters](#parameters-18)
    - [Returns](#returns-18)
    - [Examples](#examples-6)
      - [Request](#request-17)
      - [Response](#response-17)
  - [eth_getTransactionByBlockNumberAndIndex](#eth_gettransactionbyblocknumberandindex)
    - [Parameters](#parameters-19)
    - [Returns](#returns-19)
    - [Examples](#examples-7)
      - [Request](#request-18)
      - [Response](#response-18)
  - [eth_getTransactionByHash](#eth_gettransactionbyhash)
    - [Parameters](#parameters-20)
    - [Returns](#returns-20)
    - [Examples](#examples-8)
      - [Request](#request-19)
      - [Response](#response-19)
  - [eth_getTransactionCount](#eth_gettransactioncount)
    - [Parameters](#parameters-21)
    - [Returns](#returns-21)
    - [Example](#example-11)
      - [Request](#request-20)
      - [Response](#response-20)
  - [eth_getTransactionReceipt](#eth_gettransactionreceipt)
    - [Parameters](#parameters-22)
    - [Returns](#returns-22)
    - [Example](#example-12)
      - [Request](#request-21)
      - [Response](#response-21)
  - [eth_hashrate](#eth_hashrate)
    - [Parameters](#parameters-23)
    - [Returns](#returns-23)
    - [Example](#example-13)
      - [Request](#request-22)
      - [Response](#response-22)
  - [eth_mining](#eth_mining)
    - [Parameters](#parameters-24)
    - [Returns](#returns-24)
    - [Example](#example-14)
      - [Request](#request-23)
      - [Response](#response-23)
  - [eth_newBlockFilter](#eth_newblockfilter)
    - [Parameters](#parameters-25)
    - [Returns](#returns-25)
    - [Example](#example-15)
      - [Request](#request-24)
      - [Response](#response-24)
  - [eth_newFilter](#eth_newfilter)
    - [A note on specifying topic filters:](#a-note-on-specifying-topic-filters)
    - [Parameters](#parameters-26)
      - [Returns](#returns-26)
    - [Example](#example-16)
      - [Request](#request-25)
      - [Response](#response-25)
  - [eth_newPendingTransactionFilter](#eth_newpendingtransactionfilter)
      - [Parameters](#parameters-27)
      - [Returns](#returns-27)
    - [Example](#example-17)
      - [Request](#request-26)
      - [Response](#response-26)
  - [eth_protocolVersion](#eth_protocolversion)
    - [Parameters](#parameters-28)
    - [Returns](#returns-28)
    - [Example](#example-18)
      - [Request](#request-27)
      - [Response](#response-27)
  - [eth_sendRawTransaction](#eth_sendrawtransaction)
    - [Parameters](#parameters-29)
      - [Returns](#returns-29)
    - [Example](#example-19)
      - [Request](#request-28)
      - [Request](#request-29)
  - [eth_sendTransaction](#eth_sendtransaction)
    - [Parameters](#parameters-30)
    - [Returns](#returns-30)
    - [Example](#example-20)
      - [Request](#request-30)
      - [Response](#response-28)
  - [eth_sign](#eth_sign)
    - [Parameters](#parameters-31)
    - [Returns](#returns-31)
    - [Example](#example-21)
      - [Request](#request-31)
      - [Response](#response-29)
  - [eth_submitHashrate](#eth_submithashrate)
    - [Parameters](#parameters-32)
    - [Returns](#returns-32)
    - [Example](#example-22)
      - [Request](#request-32)
      - [Response](#response-30)
  - [eth_syncing](#eth_syncing)
    - [Parameters](#parameters-33)
    - [Returns](#returns-33)
    - [Example](#example-23)
      - [Request](#request-33)
      - [Response](#response-31)
  - [eth_uninstallFilter](#eth_uninstallfilter)
    - [Parameters](#parameters-34)
    - [Returns](#returns-34)
    - [Example](#example-24)
      - [Request](#request-34)
      - [Response](#response-32)
  - [eth_signTransaction](#eth_signtransaction)
    - [Parameters](#parameters-35)
    - [Returns](#returns-35)
    - [Example](#example-25)
      - [Request](#request-35)
      - [Response](#response-33)
- [net module](#net-module)
  - [net_version](#net_version)
    - [Parameters](#parameters-36)
    - [Returns](#returns-36)
    - [Example](#example-26)
      - [Request](#request-36)
      - [Response](#response-34)
  - [net_listening](#net_listening)
    - [Parameters](#parameters-37)
    - [Returns](#returns-37)
    - [Example](#example-27)
      - [Request](#request-37)
      - [Response](#response-35)
  - [net_peerCount](#net_peercount)
    - [Parameters](#parameters-38)
    - [Returns](#returns-38)
    - [Example](#example-28)
      - [Request](#request-38)
      - [Response](#response-36)
- [web3 module](#web3-module)
  - [web3_clientVersion](#web3_clientversion)
    - [Parameters](#parameters-39)
    - [Returns](#returns-39)
    - [Example](#example-29)
      - [Request](#request-39)
      - [Response](#response-37)
  - [web3_sha3](#web3_sha3)
    - [Parameters](#parameters-40)
    - [Returns](#returns-40)
    - [Example](#example-30)
      - [Request](#request-40)
      - [Response](#response-38)
- [4. Application error codes](#4-application-error-codes)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

## 1. Overview

Aion JSON-RPC API is an API for interacting with Aion nodes.  This specification defines the protocol for API; the methods of the API, and error codes used by those methods.  It is based upon [Ethereum's JSON-RPC API](https://github.com/ethereum/wiki/wiki/JSON-RPC), with modifications to accommodate the Aion blockchain.

## 2. Protocol

### Data structures

Aion JSON-RPC API uses the [JSON-RPC 2.0](https://www.jsonrpc.org/specification), a stateless, light-weight remote procedure call (RPC) protocol.  JSON-RPC defines the data structures for the requests and responses, to which this API adheres.  It is transport agnostic in that the concepts can be used within the same process, over sockets, over http, or in many various message passing environments. It uses [JSON](http://www.json.org/) ([RFC 4627](http://www.ietf.org/rfc/rfc4627.txt)) as data format.

For request, in addition to the four members defined in JSON-RPC 2.0 (`jsonrpc`, `method`, `params`, `id`), the client may optionally include an extra member:

**aion-api**: A String containing the Aion JSON-RPC API version that the request is using.  The server is not required to respect the version, but it should to do so when possible.

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

#### Default block parameter

Some methods have an extra default block parameter; for instance, [eth_getBalance](#eth_getbalance) or [eth_getTransactionCount](#eth_gettransactioncount).

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


## eth module

### eth_accounts

Returns a list of addresses owned by kernel.

#### Parameters

none

#### Returns

`Array of DATA`, 32 Bytes - addresses owned by the kernel.

#### Example

##### Request

        '{"jsonrpc":"2.0", "method":"eth_accounts", "params":[], "id":1}'

##### Response

        {
          "result": [
            "0xa06f54954371d8352ab18c2df7a711a64eb72d5e53717f281b45fd00fe4e5985",
            "0xa0252de685e6b4673a481b9ce16f196e0bef36c83e6d594543784dcd79b8d55f"
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

        '{"jsonrpc":"2.0", "method":"eth_blockNumber", "params":[],"id":1}'

#### Response

        {
          "result": 0x296bc1,
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
  - `data`: `DATA`  - (optional) Empty for pure balance transfers; otherwise, the encoding of a contract for deployment or a contract method call.  Details for encoding depends on VM for that type of transaction:
     - [AionVM ABI specification](https://github.com/aionnetwork/AVM/wiki/ABI-Specification)
     - [FastVM ABI specification](https://github.com/aionnetwork/aion_fastvm/wiki/Specifications#abi-types)
2. `QUANTITY|TAG` - (optional) integer block number, or the string `"latest"`, `"earliest"` or `"pending"`, see the [default block parameter](#default-block-parameter)

#### Returns

`DATA` - the return value of executed contract.

#### Example
##### Request

        {
          "jsonrpc": "2.0",
          "method": "eth_call",
          "params": [
            {
              "from": "0xa03a4a3ccff2b2f0f6ba368f2668126ee01e73b6b44faf801135a2d4183edb2c",
              "to": "0xa0f40004d87d2385248603022988b2ed1e9f463b1f7bc8187515984402e4934b",
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

        {"jsonrpc": "2.0", "method": "eth_coinbase", "params": [], "id": 1}

#### Response

        {
          "result": "0xa03a4a3ccff2b2f0f6ba368f2668126ee01e73b6b44faf801135a2d4183edb2c",
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
          "params": ["contract test { function multiply(uint a) returns(uint d) { return a * 7; } }"],
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
              "to": "0xa0f40004d87d2385248603022988b2ed1e9f463b1f7bc8187515984402e4934b",
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

### eth_getBalance

Returns the balance of the account of given address.

#### Parameters

1. `DATA`, 32 bytes - adddress to check for balance.
1. `QUANTITY|TAG` - (optional) integer block number, or the string "latest", "earliest" or "pending", see the [default block parameter](#default-block-parameter)

#### Returns

`QUANTITY` - integer of the current balane in nanoAmps (nAmp).

#### Examples

##### Request

        {                                                                                                                                                                                                                   
          "method": "eth_getBalance",
          "params": [
            "0xa0f37e8c51b4677a4925f6ba623319ca45262b567e98104af5879b7a88c2f25b"
          ],  
          "id": "1",
          "jsonrpc": "2.0"
        }

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

  - `number`: `QUANTITY` - the block number. `null` when it is pending block.
  - `hash`: `DATA`, 32 Bytes - hash of the block. `null` when it is pending block.
  - `parentHash`: `DATA`, 32 Bytes - hash of the parent block.
  - `nonce`: `DATA`, 32 Bytes - nonce of the generated proof-of-work. `null` when it is pending block.
  - `logsBloom`: `DATA`, 256 Bytes - the bloom filter for the logs of the block. `null` when it is pending block.
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

        {                                                                                                                                                                                                                   
          "method": "eth_getBlockByHash",
          "params": [
            "0x8f8b3dd16c6c972d0910af4c50e13b4521966ff6d9909922bc1e366461b4fe52",
            true
          ],
          "id": "1",
          "jsonrpc": "2.0"
        }

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
            "miner": "0xa0f9f5d4925501974403b34226bbf01eceba58582719c5744f86880959c052d2",
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
              {
                "nrgPrice": "0x4a817c800",
                "nrg": "0x7c1e",
                "transactionIndex": 0,
                "nonce": "0xebb",
                "input": "0x4178462f00000000000000000000000000000021",
                "blockNumber": "0x28cce9",
                "gas": "0x7c1e",
                "from": "0xa0cfe4620faef3d35f8a842e361d228520f1697eec05b643b34293b88cca2aec",
                "to": "0xa03c3dc1f9c3cc2a02155fde9ad33f80e2d0a3ea6216aba5b2d11962d25f27bf",
                "value": "0x",
                "hash": "0x8571465914fca68c39dfd127a3a429856fccc9e121ccfc276ced4f95f3331831",
                "gasPrice": "0x4a817c800",
                "timestamp": "0x5c8c04f9"
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

1. `QUANTITY|TAG`, integer of a block number, or the string "earliest", "latest" or "pending", as in the [default block parameter](#default-block-parameter)
1. `Boolean` - (optional) If `true` it returns the full transaction objects, if `false` only the hashes of the transactions.  If not provided, assumed to be `false`.

#### Returns

See [eth_getBlockByHash](#eth_getBlockByHash)

#### Examples

##### Request

    {                                                                                                                                                                                                                   
      "method": "eth_getBlockByNumber",
      "params": [
        "0x28cce9",
        "false"
      ],
      "id": "1",
      "jsonrpc": "2.0"
    }

##### Response

See [eth_getBlockByHash](#eth_getBlockByHash)

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

1. `QUANTITY|TAG` - integer of a block number, or the string `"earliest"`, `"latest"` or `"pending"`, as in the [default block parameter](#default-block-parameter)

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
2. `QUANTITY|TAG` - (optional) integer block number, or the string `"latest"`, `"earliest"` or `"pending"`, see the [default block parameter](#default-block-parameter).

#### Returns

`DATA` - the code from the given address.

#### Examples

##### Request

        {                                                                                                                                                                                                                   
          "jsonrpc": "2.0",
          "method": "eth_getCode",
          "params": [
            "0xa0f40004d87d2385248603022988b2ed1e9f463b1f7bc8187515984402e4934b",
            "latest"
          ],
          "id": 1
        }

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

        {"jsonrpc":"2.0", "method":"eth_getCompilers", "params":[], "id":1}

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
              "address": "0xa01296dd95f309110aef8ffbd3ce551d3081d08afeb12206b077ba4cd494a8d9",
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

### eth_getFilterLogs

Returns an array of all logs matching filter with given id.

#### Parameters

1. `QUANTITY` - The filter id.

#### Returns

See [eth_getFilterChanges](#eth_getfilterchanges)

##### Request

        {                                                                                                                                                                                                                   
          "jsonrpc": "2.0",
          "method": "eth_getFilterLogs",
          "params": [
            "0x16"
          ],
          "id": 74
        }

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
              "address": "0xa01296dd95f309110aef8ffbd3ce551d3081d08afeb12206b077ba4cd494a8d9",
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
              "address": "0xa01296dd95f309110aef8ffbd3ce551d3081d08afeb12206b077ba4cd494a8d9",
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
3. `QUANTITY|TAG` - integer block number, or the string `"latest"`, `"earliest"` or `"pending"`, see the [default block parameter](#default-block-parameter)

#### Returns

`DATA` - the value at this storage position.

#### Example

Calculating the correct position depends on the storage to retrieve.  The example below is for FVM contracts.

Consider the following contract deployed at `0xa0dc23204e4b0afcc0c43461777d13b67fbb77979d98c7f637adfed9086fb465` by address `0xa06a3535aeaa8f15adbaca345ad33768496597dd98a9be95c0532b27cacd14d9`.

        contract Storage {
            uint pos0;
            mapping(address => uint) pos1;
        
            function Storage() {
                pos0 = 1234;
                pos1[msg.sender] = 5678;
            }
        }

Retrieving the value of pos0 is straight forward:


        curl -X POST --data '{"jsonrpc":"2.0", "method": "eth_getStorageAt", "params": ["0xa0dc23204e4b0afcc0c43461777d13b67fbb77979d98c7f637adfed9086fb465", "0x0", "latest"], "id": 1}' localhost:8545
        
        {"jsonrpc":"2.0","id":1,"result":"0x00000000000000000000000000000000000000000000000000000000000004d2"}


Retrieving an element of the map is harder. The position of an element in the map is calculated with:

        keccack(LeftPad32(key, 0), LeftPad32(map position, 0))


This means to retrieve the storage on pos1["0xa06a3535aeaa8f15adbaca345ad33768496597dd98a9be95c0532b27cacd14d9"] we need to calculate the position with:

        keccak(decodeHex("000000000000000000000000391694e7e0b0cce554cb130d723a9d27458f9298" + "0000000000000000000000000000000000000000000000000000000000000001"))

The [aion-web3](https://github.com/aionnetwork/aion_web3/) library's console can be used to make the calculation:

        > var key = "000000000000000000000000391694e7e0b0cce554cb130d723a9d27458f9298" + "0000000000000000000000000000000000000000000000000000000000000001"
        undefined
        > web3.sha3(key, {"encoding": "hex"})
        "0x6661e9d6d8b923d5bbaab1b96e1dd51ff6ea2a93520fdc9eb75d059238b8c5e9"

Now to fetch the storage:

        curl -X POST --data '{"jsonrpc":"2.0", "method": "eth_getStorageAt", "params": ["0xa0dc23204e4b0afcc0c43461777d13b67fbb77979d98c7f637adfed9086fb465", "0x6661e9d6d8b923d5bbaab1b96e1dd51ff6ea2a93520fdc9eb75d059238b8c5e9", "latest"], "id": 1}' localhost:8545
        
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

        {                                                                                                                                                                                                                   
          "jsonrpc": "2.0",
          "method": "eth_getTransactionByBlockHashAndIndex",
          "params": [
            "0x49dc23204e4b0afcc0c43461777d13b67fbb77979d98c7f637adfed9086fb465",
            "0x6"
          ],
          "id": 1
        }

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

        {                                                                                                                                                                                                                   
          "jsonrpc": "2.0",
          "method": "eth_getTransactionByBlockNumberAndIndex",
          "params": [
            "0x6d39e",
            "0x6"
          ],
          "id": 1
        }

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

        {                                                                                                                                                                                                                   
          "method": "eth_getTransactionByHash",
          "params": [
            "0xfafcae97932003ef1b6a896d51c47b2abb88d97339861dc803d21424dfe0402b"
          ],
          "id": "1",
          "jsonrpc": "2.0"
        }

##### Response

        {   
          "result": {
            "blockHash": "0x49dc23204e4b0afcc0c43461777d13b67fbb77979d98c7f637adfed9086fb465",
            "blockNumber": "0x6d39e",
            "from": "0xa06a3535aeaa8f15adbaca345ad33768496597dd98a9be95c0532b27cacd14d9",
            "nrg": "0x7a120",
            "nrgPrice": "0x2540be400",
            "gas": "0x7a120",
            "gasPrice": "0x2540be400",
            "hash": "0xfafcae97932003ef1b6a896d51c47b2abb88d97339861dc803d21424dfe0402b",
            "input": "0x",
            "nonce": "0x1c2b",
            "to": "0xa0a6efc1029bf24d80779872781ed6efd993124e6370c04998b29f7730b8eae5",
            "transactionIndex": "0x6",
            "value": "0xd4e0885c33cf3a",
            "timestamp": "0x5b2634f8"
          },  
          "id": "1",
          "jsonrpc": "2.0"
        }


***

### eth_getTransactionCount

Returns the number of transactions *sent* from an address.

#### Parameters

1. `DATA`, 32 Bytes - address.
2. `QUANTITY|TAG` - (optional) integer block number, or the string `"latest"`, `"earliest"` or `"pending"`, see the [default block parameter](#the-default-block-parameter)

#### Returns

`QUANTITY` - integer of the number of transactions send from this address.

#### Example

##### Request

        {                                                                                                                                                                                                                   
          "jsonrpc": "2.0",
          "method": "eth_getTransactionCount",
          "params": [
            "0xa0cfe4620faef3d35f8a842e361d228520f1697eec05b643b34293b88cca2aec",
            "latest"
          ],
          "id": 1
        }

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
  - `root` : `DATA` 32 bytes of post-transaction stateroot if it was successful
  - `status`: `QUANTITY` either `1` (success) or `0` (failure)

#### Example

##### Request

        {                                                                                                                                                                                                                   
          "jsonrpc": "2.0",
          "method": "eth_getTransactionReceipt",
          "params": [
            "0x8571465914fca68c39dfd127a3a429856fccc9e121ccfc276ced4f95f3331831"
          ],
          "id": 1
        }

##### Response

        {
          "result": {
            "transactionHash": "0x8571465914fca68c39dfd127a3a429856fccc9e121ccfc276ced4f95f3331831",
            "transactionIndex": "0x0",
            "blockHash": "0x8f8b3dd16c6c972d0910af4c50e13b4521966ff6d9909922bc1e366461b4fe52",
            "blockNumber": "0x28cce9",
            "from": "0xa0cfe4620faef3d35f8a842e361d228520f1697eec05b643b34293b88cca2aec",
            "to": "0xa03c3dc1f9c3cc2a02155fde9ad33f80e2d0a3ea6216aba5b2d11962d25f27bf",
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
                "address": "0xa03c3dc1f9c3cc2a02155fde9ad33f80e2d0a3ea6216aba5b2d11962d25f27bf",
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

        {"jsonrpc": "2.0", "method": "eth_newBlockFilter", "params": [], "id": 1}

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

        {"jsonrpc": "2.0", "method": "eth_newPendingTransactionFilter", "params": [], "id": 1 }

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

        {"jsonrpc":"2.0", "method":"eth_protocolVersion", "params":[], "id":1}

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

### eth_sendTransaction

Creates new message call transaction or a contract creation, if the data field contains code.

This method requires that the account of the transaction sender be unlocked.

#### Parameters

1. `Object` - The transaction object
  - `from`: `DATA`, 32 Bytes - The address the transaction is sent from.
  - `to`: `DATA`, 32 Bytes - (empty when creating new contract) The address the transaction is directed to.
  - `gas`: `QUANTITY`  - (optional; default for contract creation: 350000; for other transactions: 90000) Integer of the energy provided for the transaction execution. It will return unused energy.
  - `gasPrice`: `QUANTITY`  - (optional, default: the value given by [eth_gasPrice](#eth_gasPrice)) Integer of the gasPrice used for each paid energy
  - `value`: `QUANTITY`  - (optional) Integer of the value sent with this transaction
  - `data`: `DATA`  - (optional) Empty for pure balance transfers; otherwise, the encoding of a contract for deployment or a contract method call.  Details for encoding depends on VM for that type of transaction:
     - [AionVM ABI specification](https://github.com/aionnetwork/AVM/wiki/ABI-Specification)
     - [FastVM ABI specification](https://github.com/aionnetwork/aion_fastvm/wiki/Specifications#abi-types)
  - `nonce`: `QUANTITY`  - (optional) Integer of a nonce. This allows to overwrite your own pending transactions that use the same nonce.
  -  `type`: `DATA` - (optional; defaults to `0x00`) Type code of transaction.

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
              "from": "0xa03a4a3ccff2b2f0f6ba368f2668126ee01e73b6b44faf801135a2d4183edb2c",
              "to": "0xa01296dd95f309110aef8ffbd3ce551d3081d08afeb12206b077ba4cd494a8d9",
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

### eth_sign

The sign method calculates an Aion-specific signature with: `sign(keccak256("\x15Aion Signed Message:\n" + len(message) + message)))`.

By adding a prefix to the message makes the calculated signature recognisable as an Ethereum specific signature. This prevents misuse where a malicious DApp can sign arbitrary data (e.g. transaction) and use the signature to impersonate the victim.

This method requires that the account of the transaction sender be unlocked.

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
            "0xa03a4a3ccff2b2f0f6ba368f2668126ee01e73b6b44faf801135a2d4183edb2c",
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

        {"jsonrpc": "2.0", "method": "eth_syncing", "params": [], "id": 1}

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

### eth_signTransaction

Signs a transaction object (but does not send it).

This method requires that the account of the transaction sender be unlocked.

#### Parameters

1. `Object` - The transaction object (see [eth_sendTransaction](#eth_sendTransaction))

#### Returns

`Object`, the transaction object and raw signed transaction

  - `tx`: `Object` - The transaction that was signed (missing optional parameters are automatically populated by the kernel and those values are shown here). See parameters of [eth_sendTransaction](#eth_sendTransaction) for details on the transaction object.

#### Example

##### Request

        {
          "jsonrpc": "2.0",
          "method": "eth_signTransaction",
          "params": [
            {
              "from": "0xa03a4a3ccff2b2f0f6ba368f2668126ee01e73b6b44faf801135a2d4183edb2c",
              "to": "0xa01296dd95f309110aef8ffbd3ce551d3081d08afeb12206b077ba4cd494a8d9",
              "value": "0xa",
              "gas": "0xea60",
              "gasPrice": "0x2540be400"
            },
            "0xa03a4a3ccff2b2f0f6ba368f2668126ee01e73b6b44faf801135a2d4183edb2c"
          ],
          "id": 1
        }

##### Response

        {
          "result": {
            "tx": {
              "nrgPrice": "0x2540be400",
              "input": "0x",
              "nrg": "0xea60",
              "gas": "0xea60",
              "to": "0xa01296dd95f309110aef8ffbd3ce551d3081d08afeb12206b077ba4cd494a8d9",
              "nonce": "0x0d",
              "value": "0x0a",
              "hash": "0x3ad98b558a9b59f00c2326d5027dc473fea23c773a4a5b0a374bc7c6d8b43f1e",
              "gasPrice": "0x2540be400"
              "timestamp": "0x5c8c04f9", //TODO 
              "signature": "", //TODO
              "type": "0x00"
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

`String` - The current network id.
- `"1"`: Aion Mainnet
- `"32"`: Mastery Testnet

#### Example

##### Request

        {"jsonrpc": "2.0", "method": "net_version", "params": [], "id": 1}

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

        {"jsonrpc": "2.0", "method": "net_listening", "params": [], "id": 1}

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

        {"jsonrpc": "2.0", "method": "net_peerCount", "params": [], "id": 1}

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

        {"jsonrpc": "2.0", "method": "web3_clientVersion", "params": [], "id": 1}

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

