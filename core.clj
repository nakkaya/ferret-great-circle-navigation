(def pi 3.14159265358979323846)
(def earth-radius 6371.0)

;; calculate the initial bearing (sometimes referred to as forward azimuth) which if
;; followed in a straight line along a great-circle arc will take you from the start
;; point to the end point - in degrees
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

;; calculate the great-circle distance between two points - in km
(defn distance [c1 c2]
  (let [[lat1 lon1] (map #(to-radians %) c1)
        [lat2 lon2] (map #(to-radians %) c2)]
    (* 2 earth-radius
       (asin
        (sqrt
         (+ (pow (sin (/ (- lat1 lat2) 2)) 2)
            (* (cos lat1)
               (cos lat2)
               (pow (sin (/ (- lon1 lon2) 2)) 2))))))))

;;returns (signed) distance from loc point to great circle defined by start-point and end-point. - in km
(defn crosstrack-distance [start end loc]
  (let [dist-start-loc    (distance start loc)
        bearing-start-loc (bearing start loc)
        bearing-start-end (bearing start end)]

    (* (asin (* (sin (/ dist-start-loc earth-radius))
                (sin (to-radians (- bearing-start-loc bearing-start-end)))))
       earth-radius)))
