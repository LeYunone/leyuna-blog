package com.blog.api.lucene;

import java.io.IOException;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

public final class MyIKTokenizer extends Tokenizer {
    private IKSegmenter _IKImplement;
    private final CharTermAttribute termAtt = (CharTermAttribute)this.addAttribute(CharTermAttribute.class);
    private final OffsetAttribute offsetAtt = (OffsetAttribute)this.addAttribute(OffsetAttribute.class);
    private final TypeAttribute typeAtt = (TypeAttribute)this.addAttribute(TypeAttribute.class);
    private int endPosition;

    public MyIKTokenizer(boolean useSmart) {
        this._IKImplement = new IKSegmenter(this.input, useSmart);
    }

    public boolean incrementToken() throws IOException {
        this.clearAttributes();
        Lexeme nextLexeme = this._IKImplement.next();
        if (nextLexeme != null) {
            this.termAtt.append(nextLexeme.getLexemeText());
            this.termAtt.setLength(nextLexeme.getLength());
            this.offsetAtt.setOffset(nextLexeme.getBeginPosition(), nextLexeme.getEndPosition());
            this.endPosition = nextLexeme.getEndPosition();
            this.typeAtt.setType(nextLexeme.getLexemeTypeString());
            return true;
        } else {
            return false;
        }
    }

    public void reset() throws IOException {
        super.reset();
        this._IKImplement.reset(this.input);
    }

    public final void end() {
        int finalOffset = this.correctOffset(this.endPosition);
        this.offsetAtt.setOffset(finalOffset, finalOffset);
    }
}