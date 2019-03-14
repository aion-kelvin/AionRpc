| method        | aion impl | aionr impl | Eth spec | New spec | Notes |
|---------------|------|-------|----------|-----------------------|-------|
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
|eth_getProof |✗|✗|✓|✓| Was recently added to Eth (see [2]); we should add it too 
|eth_getUncleByBlockHashAndIndex |✗|✗|✓|✗ |Aion doesn't have 'uncle'; see [1] | 
|eth_getUncleByBlockNumberAndIndex |✗|✗|✓|✗ |Aion doesn't have 'uncle'; see [1] | 
|eth_getUncleCountByBlockHash |✗|✗|✓|✗ |Aion doesn't have 'uncle'; see [1] | 
|eth_getUncleCountByBlockNumber |✗|✗|✓|✗ |Aion doesn't have 'uncle'; see [1] | 
|eth_getWork |✗|✓|✓|✗| In aion, fulfilled by getblocktemplate; see [3]
|eth_submitWork |✗|✓|✓|✗| In aion, fulfilled by submitblock; see [3] |  
|eth_subscribe |✗|✓|✗|✗|Not in core Eth spec 
|eth_unsubscribe |✗|✓|✗|✗ |Not in core Eth spec
