;Object expressions beginning

(declare negate add subtract multiply divide exp ln mean varn sumexp softmax objectEntries toStringSuffix)

(defmulti chooseDiff (fn [sign exps var] sign))

(definterface CommonObject
  (^Number evaluate [args])
  (diff [var])
  (toStringSuffix [])
  )

(deftype AbstractConstant [value]
  CommonObject
  (evaluate [this args] value)
  (diff [this var] (AbstractConstant. 0))
  (toStringSuffix [this] (str value))
  Object
  (toString [this] (str value))
  )

(deftype AbstractVariable [variable]
  CommonObject
  (evaluate [this args] (args (clojure.string/lower-case (str (get variable 0)))))
  (diff [this v] (cond
                   (= v (clojure.string/lower-case (str (get variable 0)))) (AbstractConstant. 1)
                   :else (AbstractConstant. 0)))
  (toStringSuffix [this] (str variable))
  Object
  (toString [this] (str variable))
  )


(deftype AbstractOperation [sign exps]
  CommonObject
  (evaluate [this args] (apply (objectEntries sign) (map #(.evaluate % args) exps)))
  (diff [this v] (chooseDiff sign exps v))
  (toStringSuffix [this] (str "(" (clojure.string/join " " (map toStringSuffix exps)) " " sign ")"))
  Object
  (toString [this] (str "(" sign " " (clojure.string/join " " exps) ")"))
  )

(defn Constant [value] (AbstractConstant. value))
(defn Variable [var]  (AbstractVariable. var))

(defn makeOperation [sign] (fn [& exps] (AbstractOperation. sign exps)))

(def Negate (makeOperation 'negate))
(def Add (makeOperation '+))
(def Subtract (makeOperation '-))
(def Multiply (makeOperation '*))
(def Divide (makeOperation '/))
(def Exp (makeOperation 'exp))
(def Ln (makeOperation 'ln))
(def Mean (makeOperation 'mean))
(def Varn (makeOperation 'varn))

(def objectEntries {'negate  -
                    '+       +
                    '-       -
                    '*       *
                    '/       (fn [& args] (if (= (count args) 1)
                                            (/ 1.0 (double (first args)))
                                            (reduce (fn [x, y] (/ (double x) (double y))) args)))
                    'exp     (fn [value] (Math/exp value))
                    'ln      (fn [value] (Math/log (Math/abs ^double value)))
                    'mean    (fn [& args] (/ (apply + args) (count args)))
                    'varn    (fn [& args] (- (/ (apply + (map #(Math/pow % 2) args)) (count args))
                                             (Math/pow (/ (apply + args) (count args)) 2)))
                    'sumexp  (fn [& args] (apply + (map #(Math/pow Math/E %) args)))
                    'softmax (fn [& args] (/ (Math/pow Math/E (first args)) (apply + (map #(Math/pow Math/E %) args))))
                    })

(defn diff [this v] (.diff this v))
(defn toString [this] (.toString this))
(defn toStringSuffix[this] (.toStringSuffix this))
(defn evaluate [this args] (.evaluate this args))


(defmethod chooseDiff 'mean [sign exps var] (let [x (AbstractOperation. '+ exps)
                                                  y (Constant (count exps))]
                                              (chooseDiff '/ (sequence [x y]) var)))
(defmethod chooseDiff 'varn [sign exps var] (let [x (chooseDiff 'mean (map #(Multiply % %) exps) var)
                                                  y (AbstractOperation. 'mean exps)]
                                              (Subtract x (diff (Multiply y y) var)
                                                        )))
(defmethod chooseDiff 'exp [sign exps var] (Multiply (Exp (first exps)) (diff (first exps) var)))
(defmethod chooseDiff 'ln [sign exps var] (Multiply (Divide (Constant 1.0) (first exps)) (diff (first exps) var)))

(defmethod chooseDiff '* [sign exps var] (letfn [(func [x y] (Add (Multiply (diff x var) y)
                                                                  (Multiply x (diff y var))))]
                                           (cond (= (count exps) 1) (diff (first exps) var)
                                                 :else (func (first exps) (AbstractOperation. '* (rest exps))))))
(defmethod chooseDiff '/ [sign exps var] (letfn [(func [x y] (Divide (Subtract
                                                                       (Multiply (diff x var) y)
                                                                       (Multiply x (diff y var)))
                                                                     (Multiply y y)))]
                                           (cond (= (count exps) 1) (func (Constant 1.0) (first exps))
                                                 :else (func (first exps) (AbstractOperation. '* (rest exps))))))
(defmethod chooseDiff :default [sign exps var] (AbstractOperation. sign (map #(diff % var) exps)))


(defn parseObjectExpressions [expr]
  (if (number? expr)
    (Constant expr)
    (if (contains? #{'x 'y 'z} expr)
      (Variable (str expr))
      (AbstractOperation. (first expr) (map parseObjectExpressions (rest expr)))))
  )

(defn parseObject [expr] (parseObjectExpressions (read-string expr)))

;Object expressions ending


;functional expressions beginning

(def constant constantly)
(defn variable [var] (fn [args] (double (args var))))

(defn operation [sign] (fn [& exps] (fn [args] (apply (objectEntries sign) (map (fn [x] (x args)) exps)))))

(def negate (operation '-))
(def exp (operation 'exp))
(def ln (operation 'ln))
(def add (operation '+))
(def subtract (operation '-))
(def multiply (operation '*))
(def divide (operation '/))
(def mean (operation 'mean))
(def varn (operation 'varn))
(def sumexp (operation 'sumexp))
(def softmax (operation 'softmax))

(def functionalEntries {'negate  negate
                        '+       add
                        '-       subtract
                        '*       multiply
                        '/       divide
                        'exp     exp
                        'ln      ln
                        'mean    mean
                        'varn    varn
                        'sumexp  sumexp
                        'softmax softmax
                        })

(defn parseFunctionExpressions [expr]
  (if (number? expr)
    (constant expr)
    (if (contains? #{'x 'y 'z} expr)
      (variable (str expr))
      (apply (functionalEntries (first expr)) (map parseFunctionExpressions (rest expr)))))
  )

(defn parseFunction [expr] (parseFunctionExpressions (read-string expr)))

;functional expressions ending



;combination parser beginning

(load-file "parser.clj")

(defn +string [s] (apply +seqf (constantly s) (mapv +char (clojure.string/split s #""))))

(def *digit (+char "0123456789"))
(def *number (+map read-string (+str (+seqf #(flatten %&) (+opt (+char "+-")) (+plus *digit) (+opt (+seq (+char ".") (+plus *digit)))))))
(def *space (+char " \t\n\r"))
(def *ws (+ignore (+star *space)))

(def parseObjectSuffix
    (letfn [(*constant [] (+map Constant *number))
            (*variable [] (+map #(Variable %)
                                (+str (+plus (+char "xyzXYZ")))))
            (*operation [] (+seqf #(AbstractOperation. (read-string (last (flatten %&))) (butlast (flatten %&))) *ws
                                  (+plus (+seqn 0 (*expression) *ws)) (apply +or (map #(+string (str %)) (keys objectEntries))) *ws))
            (*parenthesis [] (+seqn 1 (+char "(") (delay (*operation)) (+char ")")))
            (*expression [] (+or (*constant) (*variable) (*parenthesis)))]
      (+parser (+seqn 0 *ws (*expression) *ws))))

(println (toStringSuffix (parseObjectSuffix "((xyZ exp) 2 /)")))



;combination parser ending