(defn vop [op] (fn [v1, v2] (mapv op v1 v2)))

(def v+ (vop +))
(def v- (vop -))
(def v* (vop *))
(def vd (vop /))

(defn v*s [v, sc] (mapv (partial * sc) v))

(defn scalar [v1, v2] (apply + (v* v1 v2)))

(defn vect [v1, v2] (vector (- (* (v1 1) (v2 2)) (* (v1 2) (v2 1)))
                            (- (* (v1 2) (v2 0) ) (* (v1 0) (v2 2)))
                            (- (* (v1 0) (v2 1)) (* (v1 1) (v2 0)))))

(def m+ (vop v+))
(def m- (vop v-))
(def m* (vop v*))
(def md (vop vd))

(defn m*s [mt, sc] (mapv #(v*s % sc) mt))
(defn m*v [mt, v] (mapv #(apply + (v* % v)) mt))
(defn transpose [mt] (apply mapv vector mt))
(defn m*m [mt1, mt2] (mapv #(m*v (transpose mt2) %) mt1))

(def c+ (vop m+))
(def c- (vop m-))
(def c* (vop m*))
(def cd (vop md))