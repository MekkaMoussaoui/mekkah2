package dz.atelier.ntic.remplircases.game;

public class
Cat {
    String word_sphinx;
    int image_sphinx;
    String grammar_sphinx;

    public Cat(String word_sphinx, int image_sphinx, String grammar_sphinx) {
        this.image_sphinx = image_sphinx;
        this.grammar_sphinx = grammar_sphinx;
        this.word_sphinx = word_sphinx;
    }

    public String getWord_sphinx() {
        return word_sphinx;
    }

    public int getImage_sphinx() {
        return image_sphinx;
    }

    public String getGrammar_sphinx() {
        return grammar_sphinx;
    }
}
