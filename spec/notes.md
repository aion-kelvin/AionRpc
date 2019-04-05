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
|eth_subscribe |✗|✓|✗|✗|Not in core Eth spec 
|eth_unsubscribe |✗|✓|✗|✗ |Not in core Eth spec

#### notes:

[1] https://github.com/aionnetwork/aion/wiki/JSON-RPC-API-Docs#21-unsupported-endpoints

[2] https://github.com/ethereum/EIPs/issues/1186

[3] `getblocktemplate` and `submitblock` are names from BTC (https://en.bitcoin.it/wiki/Original_Bitcoin_client/API_calls_list).  When Aion RPC server was implemented, we were testing against a BTC miner which expected those names.  

### Spec TODOs

- sanitize examples
