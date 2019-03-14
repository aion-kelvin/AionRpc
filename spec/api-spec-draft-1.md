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

### eth_accounts [EXAMPLE]

Returns a list of addresses owned by kernel.

#### Parameters

None

#### Returns

* `Array` - 64 Bytes - addresses owned by the kernel.

#### Examples

##### Request

        curl --data '{"method":"TODO","params":["TODO"],"id":"TODO","jsonrpc":"2.0"}' -H "Content-Type: application/json" -X POST localhost:8545

##### Response

        {
          "id": 1,
          "jsonrpc": "2.0",
          "result": ["TODO"]
        }


### eth_blockNumber
### eth_call
### eth_coinbase
### eth_compileSolidity
### eth_estimateGas
### eth_gasPrice
### eth_getBalance [DIFF]
### eth_getBlockByHash
### eth_getBlockByNumber
### eth_getBlockTransactionCountByHash
### eth_getBlockTransactionCountByNumber
### eth_getCode
### eth_getCompilers
### eth_getFilterChanges
### eth_getFilterLogs
### eth_getLogs
### eth_getStorageAt
### eth_getTransactionByBlockHashAndIndex
### eth_getTransactionByBlockNumberAndIndex
### eth_getTransactionByHash
### eth_getTransactionCount
### eth_getTransactionReceipt
### eth_hashrate
### eth_mining
### eth_newBlockFilter
### eth_newFilter
### eth_newPendingTransactionFilter
### eth_protocolVersion
### eth_sendRawTransaction
### eth_sendTransaction
### eth_sign
### eth_submitHashrate
### eth_syncing
### eth_uninstallFilter
### eth_signTransaction
### eth_getProof



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
