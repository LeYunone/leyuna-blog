package com.leyuna.blog.lucene;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;

public final class MyIKAnalyzer extends Analyzer {
    private boolean useSmart;

    public boolean useSmart() {
        return this.useSmart;
    }

    public void setUseSmart(boolean useSmart) {
        this.useSmart = useSmart;
    }

    public MyIKAnalyzer() {
        this(false);
    }

    @Override
    protected TokenStreamComponents createComponents(String s) {
        Tokenizer _MyIKTokenizer = new MyIKTokenizer(this.useSmart());
        return new TokenStreamComponents(_MyIKTokenizer);
    }

    public MyIKAnalyzer(boolean useSmart) {
        this.useSmart = useSmart;
    }

}