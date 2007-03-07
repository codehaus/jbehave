package com.sirenian.hellbound.domain.glyph;

public abstract class GlyphMovement {
   
    public static final GlyphMovement DOWN = new GlyphMovement("down"){
        public boolean performOn(LivingGlyph glyph) {
            return glyph.requestMoveDown();
        }
    };
    
    public static final GlyphMovement LEFT = new GlyphMovement("left"){
        public boolean performOn(LivingGlyph glyph) {
            return glyph.requestMoveLeft();
        }
    };
    
    public static final GlyphMovement RIGHT = new GlyphMovement("right"){
        public boolean performOn(LivingGlyph glyph) {
            return glyph.requestMoveRight();
        }
    };
    
    public static final GlyphMovement ROTATE_LEFT = new GlyphMovement("rotate left"){
        public boolean performOn(LivingGlyph glyph) {
            return glyph.requestRotateLeft();
        }
    };
    
    public static final GlyphMovement ROTATE_RIGHT = new GlyphMovement("rotate right"){
        public boolean performOn(LivingGlyph glyph) {
            return glyph.requestRotateRight();
        }
    };
    
    public static final GlyphMovement DROP = new GlyphMovement("drop"){
        public boolean performOn(LivingGlyph glyph) {
            glyph.drop();
            return true;
        }
    };

    private final String description;

    public GlyphMovement(String description) {
        this.description = description;
    }
    
    public abstract boolean performOn(LivingGlyph glyph);
    
    public String toString() {
        return description;
    }
}
