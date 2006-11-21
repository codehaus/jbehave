package com.sirenian.hellbound.engine;

import com.sirenian.hellbound.domain.Segments;

public interface CollisionDetector {

	CollisionDetector NULL = new CollisionDetector() {
		public boolean collides(Segments segments) {
			return true; // not false, or could get into real trouble.
		}
	};

	boolean collides(Segments segments);
}
