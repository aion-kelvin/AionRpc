|                                                   | Useful for public? | Notes                                                                                                                                                                                                           |
|---------------------------------------------------|--------------------|-------|
| debug_getBlocksByNumber                           | no                 | Accepts and returns almost the same thing as eth_getBlocksByNumber, except the transaction object has a boolean "mainchain" field                                                                               |
| dumpprivkey                                       | no?                | Corresponds to a method from original bitcoin API, but our impl always returns empty string                                                                                                                     |
| eth_compileSolidityZip                            | yes                | Compiles multiple sol files                                                                                                                                                                                     |
| getblocktemplate                                  | yes                | For external miners; uses name from Bitcoin API.  Replaces eth_getWork (from Eth JSON-RPC spec)                                                                                                                 |
| getdifficulty                                     | yes                | For external miners; uses name from Bitcoin API.  It just returns difficulty of best block.                                                                                                                     |
| getHeaderByBlockNumber                            | yes                | For external miners; uses name from Bitcoin API.                                                                                                                                                                |
| getinfo                                           | ??                 | For external miners; uses name from Bitcoin API.  All the fields are hardcoded except number of peers...                                                                                                        |
| getMinerStats                                     | yes                | Seems like it was introduced by Aion.  Has some useful info not found in other places: `{"result":{"minerHashrateShare":0,"minerHashrate":"0.00000","networkHashrate":"735629.7143"},"id":"1","jsonrpc":"2.0"}` |
| getmininginfo                                     | yes                | For external miners; uses name from Bitcoin API.                                                                                                                                                                |
| ops_getAccountState                               | no                 | Returns the balance an nonce for the account, plus the best block used for those queries.                                                                                                                       |
| ops_getBlock                                      | no                 | Very similar to eth_getBlockByNumber but has some extra fields, i.e. txTrieRoot, receiptTxRoot                                                                                                                  |
| ops_getChainHeadView                              | no                 | Returns 20 most recent blocks.                                                                                                                                                                                  |
| ops_getChainHeadViewBestBlock                     | no                 | Returns best block number?  (when I tried it out, it was 2 behind the result given by get_blockNumber...)                                                                                                       |
| ops_getTransaction                                | no                 | Really similar to eth_getTransactionByHash, but more optimized and missing cumulative energy                                                                                                                    |
| ops_getTransctionReceiptByTransactionAndBlockHash | no                 | Result is same as eth_getTransactionReceipt, but cumulative energy always seems to be 0, and it runs faster (according to code comments)                                                                        |
| ops_getTransactionReceiptByTransactionHash        | no                 | Same as above, but less fast because you don't tell it the block hash                                                                                                                                           |
| ops_getTransactionReceiptListByBlockHash          | no                 | Does what the name says.  Cumulative energy also always appears to be 0 here.                                                                                                                                   |
| personal_listAccounts                             | yes                |                                                                                                                                                                                                                 |
| personal_lockAccount                              | yes                |                                                                                                                                                                                                                 |
| personal_newAccount                               | yes                |                                                                                                                                                                                                                 |
| personal_unlockAccount                            | yes                |                                                                                                                                                                                                                 |
| ping                                              | no                 | returns `{"result":"pong","id":"1","jsonrpc":"2.0"}`                                                                                                                                                            |
| priv_config                                       | no                 | returns a jsonified version of config.xml.  Could be useful for test automation.                                                                                                                                |
| priv_dumpBlockByHash                              | no                 | Really similar to ops_getBlock, but seems to include an RLP-encoding of the whole block (?) in a field named `raw`                                                                                              |
| priv_dumpBlockByNumber                            | no                 | Same as above                                                                                                                                                                                                   |
| priv_dumpTransaction                              | no                 | Really similar to ops_getTransaction, but seems to include an RLP-encoding of the transaction in a field named `raw`                                                                                            |
| priv_getPendingSize                               | no                 | gets pending state transactions size                                                                                                                                                                            |
| priv_getPendingTransactions                       | no *               | gets pending transactions.  this might actually be useful for the public.  but if we add it, it should be named differently and conform with the format of aion json-rpc spec                                   |
| priv_p2pConfig                                    | no                 | shows the local address and port that p2p is bound to                                                                                                                                                           |
| priv_peers                                        | no                 | shows details about peers                                                                                                                                                                                       |
| priv_shortStats                                   | no                 | shows a bunch of diagnostic info.  seems like mostly stuff that can be accessed through other methods                                                                                                           |
| priv_syncPeers                                    | no                 | like priv_peers, but only peers that the node is actively syncing with                                                                                                                                          |
| submitblock                                       | yes                | For external miners; uses name from Bitcoin API.  Fulfils the same purpose as `eth_submitWork` from Eth json-rpc                                                                                                |
| validateaddress                                   | yes                | For external miners; uses name from Bitcoin API.                                                                                                                                                                |