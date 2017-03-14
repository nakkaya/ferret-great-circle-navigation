(def pi 3.14159265358979323846)

(defn bearing [c1 c2]
  (let [[lat1 lon1] (map #(to-radians %) c1)
        [lat2 lon2] (map #(to-radians %) c2)]
    (to-degrees
     (mod (atan2
           (* (sin (- lon2 lon1)) (cos lat2))
           (- (* (cos lat1)
                 (sin lat2))
              (* (sin lat1) 
                 (cos lat2)
                 (cos (- lon2 lon1)))))
          (* 2 pi)))))

(defn distance [c1 c2]
  (let [[lat1 lon1] (map #(to-radians %) c1)
        [lat2 lon2] (map #(to-radians %) c2)]
    (* 2 6371.0
       (asin
        (sqrt
         (+ (pow (sin (/ (- lat1 lat2) 2)) 2)
            (* (cos lat1)
               (cos lat2)
               (pow (sin (/ (- lon1 lon2) 2)) 2))))))))
