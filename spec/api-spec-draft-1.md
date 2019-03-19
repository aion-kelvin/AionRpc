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

At present there are two key datatypes that are passed over JSON (except for `eth_hashrate`, will be elaborated upon below): unformatted byte arrays and quantities. Both are passed with a hex encoding, however with different requirements to formatting:

When encoding **QUANTITIES** (integers, numbers): encode as hex, prefix with "0x", the most compact representation (slight exception: zero should be represented as "0x0"). Examples:

- 0x41 (65 in decimal) 
- 0x400 (1024 in decimal)
- WRONG: 0x (should always have at least one digit - zero is "0x0")
- WRONG: 0x0400 (no leading zeroes allowed)
- WRONG: ff (must be prefixed 0x)

When encoding **UNFORMATTED DATA** (byte arrays, account addresses, hashes, bytecode arrays): encode as hex, prefix with "0x", two hex digits per byte. Examples:

- 0x41 (size 1, "A")
- 0x004200 (size 3, "\0B\0")
- 0x (size 0, "")
- WRONG: 0xf0f0f (must be even number of digits)
- WRONG: 004200 (must be prefixed 0x)

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

### eth_accounts [EXAMPLE]

Returns a list of addresses owned by kernel.

#### Parameters

None

#### Returns

`Array` - 32 bytes - addresses owned by the kernel.

#### Examples

##### Request

        curl --data '{"method":"TODO","params":["TODO"],"id":"TODO","jsonrpc":"2.0"}' -H "Content-Type: application/json" -X POST localhost:8545

##### Response

        {
          "id": 1,
          "jsonrpc": "2.0",
          "result": ["TODO"]
        }



***

### eth_blockNumber

***

### eth_call

***

### eth_coinbase

***

### eth_compileSolidity

***

### eth_estimateGas

***

### eth_gasPrice

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

***

### eth_getBlockTransactionCountByNumber

***

### eth_getCode

***

### eth_getCompilers

***

### eth_getFilterChanges

***

### eth_getFilterLogs

***

### eth_getLogs

***

### eth_getStorageAt

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

### eth_getTransactionByBlockNumberAndIndex [JAVA]

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

##### Parameters
none

##### Returns

`Boolean` - returns `true` of the client is mining, otherwise `false`.

#### Example

##### Request

        {"jsonrpc":"2.0","method":"eth_mining","params":[],"id":1}

##### Response

        { "id":1, "jsonrpc": "2.0", "result": false }

***

### eth_newBlockFilter

***

### eth_newFilter

***

### eth_newPendingTransactionFilter

***

### eth_protocolVersion

***

### eth_sendRawTransaction

***

### eth_sendTransaction

***

### eth_sign

***

### eth_submitHashrate

***

### eth_syncing

***

### eth_uninstallFilter

***

### eth_signTransaction

***

### eth_getProof

***

## net module

## web3 module

## 4. Application error codes

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
|eth_getProof |✗|✗|✓|✓| Was recently added to Eth (see [2]); we should add it too <span style="color:red">[@chAion]</span>
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