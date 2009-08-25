package com.sirenian.hellbound.domain;

/**
 * Used because I keep accidentally changing the Segments' segments instead of creating a new array.
 * Can also put stupid error checking here - it should never be needed in Hellbound, but helps
 * with development.
 */
class ImmutableSegments {

    private final Segment[] segments;

    public ImmutableSegments(Segment[] segments) {
        for (int i = 0; i < segments.length; i++) {
            if (segments[i] == null) {
                throw new IllegalArgumentException("Segments should never be null.");
            }
            for (int j = 0; j < segments.length; j++) {
                if (i != j && segments[i].equals(segments[j])) {
                    throw new IllegalArgumentException("Segments should not contain identical segments.");
                }
            }
        }
        this.segments = segments;
    }
    
    Segment get(int index) {
        return segments[index];
    }

    int size() {
        return segments.length;
    }
    
    public String toString() {
        StringBuffer builder = new StringBuffer();
        builder.append("Segments: ");
        for (int i = 0; i < segments.length; i++) {
            builder.append(segments[i].toString());
        }
        return builder.toString();        
    }
}
