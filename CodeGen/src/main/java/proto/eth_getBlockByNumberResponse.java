package proto;

public class eth_getBlockByNumberResponse {
    private java.math.BigInteger number;
    private byte[] hash;
    private byte[] parentHash;
    private byte[] nonce;
    private byte[] logsBloom;
    private byte[] transactionsRoot;
    private byte[] stateRoot;
    private byte[] receiptsRoot;
    private byte[] miner;
    private java.math.BigInteger difficulty;
    private java.math.BigInteger totalDifficulty;
    private byte[] extraData;
    private java.math.BigInteger size;
    private java.math.BigInteger nrgLimit;
    private java.math.BigInteger nrgUsed;
    private java.math.BigInteger gasLimit;
    private java.math.BigInteger gasUsed;
    private java.math.BigInteger timestamp;
    private java.lang.Object[] transactions;
    private byte[] solution;

    public eth_getBlockByNumberResponse(
            java.math.BigInteger number,
            byte[] hash,
            byte[] parentHash,
            byte[] nonce,
            byte[] logsBloom,
            byte[] transactionsRoot,
            byte[] stateRoot,
            byte[] receiptsRoot,
            byte[] miner,
            java.math.BigInteger difficulty,
            java.math.BigInteger totalDifficulty,
            byte[] extraData,
            java.math.BigInteger size,
            java.math.BigInteger nrgLimit,
            java.math.BigInteger nrgUsed,
            java.math.BigInteger gasLimit,
            java.math.BigInteger gasUsed,
            java.math.BigInteger timestamp,
            java.lang.Object[] transactions,
            byte[] solution
    ) {
        this.number = number;
        this.hash = hash;
        this.parentHash = parentHash;
        this.nonce = nonce;
        this.logsBloom = logsBloom;
        this.transactionsRoot = transactionsRoot;
        this.stateRoot = stateRoot;
        this.receiptsRoot = receiptsRoot;
        this.miner = miner;
        this.difficulty = difficulty;
        this.totalDifficulty = totalDifficulty;
        this.extraData = extraData;
        this.size = size;
        this.nrgLimit = nrgLimit;
        this.nrgUsed = nrgUsed;
        this.gasLimit = gasLimit;
        this.gasUsed = gasUsed;
        this.timestamp = timestamp;
        this.transactions = transactions;
        this.solution = solution;
    }
    public java.math.BigInteger getNumber() {
        return this.number;
    }
    public byte[] getHash() {
        return this.hash;
    }
    public byte[] getParentHash() {
        return this.parentHash;
    }
    public byte[] getNonce() {
        return this.nonce;
    }
    public byte[] getLogsBloom() {
        return this.logsBloom;
    }
    public byte[] getTransactionsRoot() {
        return this.transactionsRoot;
    }
    public byte[] getStateRoot() {
        return this.stateRoot;
    }
    public byte[] getReceiptsRoot() {
        return this.receiptsRoot;
    }
    public byte[] getMiner() {
        return this.miner;
    }
    public java.math.BigInteger getDifficulty() {
        return this.difficulty;
    }
    public java.math.BigInteger getTotalDifficulty() {
        return this.totalDifficulty;
    }
    public byte[] getExtraData() {
        return this.extraData;
    }
    public java.math.BigInteger getSize() {
        return this.size;
    }
    public java.math.BigInteger getNrgLimit() {
        return this.nrgLimit;
    }
    public java.math.BigInteger getNrgUsed() {
        return this.nrgUsed;
    }
    public java.math.BigInteger getGasLimit() {
        return this.gasLimit;
    }
    public java.math.BigInteger getGasUsed() {
        return this.gasUsed;
    }
    public java.math.BigInteger getTimestamp() {
        return this.timestamp;
    }
    public java.lang.Object[] getTransactions() {
        return this.transactions;
    }
    public byte[] getSolution() {
        return this.solution;
    }
}