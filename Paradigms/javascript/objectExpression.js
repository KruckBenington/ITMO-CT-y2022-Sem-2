class Const {
    constructor(value) {
        this.value = value;
    }

    evaluate(x, y, z) {
        return this.value;
    }

    toString() {
        return this.value.toString();
    }

    prefix() {
        return this.toString();
    }
}

class Variable {
    constructor(v) {
        this.v = v;
    }

    evaluate(x, y, z) {
        return (this.v === 'x' ? x : this.v === 'y' ? y : z);
    }

    toString() {
        return this.v;
    }

    prefix() {
        return this.toString();
    }
}

class Operations {
    constructor(count, sign, ...expressions) {
        this.expressions = expressions;
        this.count = count;
        this.sign = sign;
    }

    evaluate(x, y, z) {
        return this.count(...this.expressions.map((el) => el.evaluate(x, y, z)));
    }

    toString() {
        return this.expressions.map((el) => el.toString()).join(' ') + ' ' + this.sign;
    }

    prefix() {
        return '(' + this.sign + ' ' + this.expressions.map((el) => el.prefix()).join(' ') + ')';
    }
}

class Add extends Operations {
    constructor(exp1, exp2) {
        super((a, b) => a + b, "+", exp1, exp2);
    }
}

class Subtract extends Operations {
    constructor(exp1, exp2) {
        super((a, b) => a - b, "-", exp1, exp2);
    }
}

class Multiply extends Operations {
    constructor(exp1, exp2) {
        super((a, b) => a * b, "*", exp1, exp2);
    }
}

class Divide extends Operations {
    constructor(exp1, exp2) {
        super((a, b) => a / b, "/", exp1, exp2);
    }
}

class Negate extends Operations {
    constructor(exp) {
        super(a => -a, "negate", exp);
    }
}

class Min3 extends Operations {
    constructor(exp1, exp2, exp3) {
        super(Math.min, "min3", exp1, exp2, exp3);
    }
}

class Max5 extends Operations {
    constructor(exp1, exp2, exp3, exp4, exp5) {
        super(Math.max, "max5", exp1, exp2, exp3, exp4, exp5);
    }
}

class ParsingError extends Error {
    constructor(message, name) {
        super(message || "Error in parsing");
        this.name = name;
    }
}


class WrongBracketSequenceError extends ParsingError {
    constructor(message) {
        super(message, "WrongBracketSequenceError");
    }
}


class EOFError extends ParsingError {
    constructor(message) {
        super(message, "EOFError");
    }
}


class WrongExpressionError extends ParsingError {
    constructor(message) {
        super(message, "WrongExpressionError");
    }
}


class Sinh extends Operations {
    constructor(exp) {
        super(Math.sinh, "sinh", exp);
    }
}

class Cosh extends Operations {
    constructor(exp) {
        super(Math.cosh, "cosh", exp);
    }
}

const postEntry = {
    'x': () => new Variable('x'),
    'y': () => new Variable('y'),
    'z': () => new Variable('z'),
    '+': (stack) => new Add(...stack.splice(-numOfArgs['+'], numOfArgs['+'])),
    '-': (stack) => new Subtract(...stack.splice(-numOfArgs['-'], numOfArgs['-'])),
    '*': (stack) => new Multiply(...stack.splice(-numOfArgs['*'], numOfArgs['*'])),
    '/': (stack) => new Divide(...stack.splice(-numOfArgs['/'], numOfArgs['/'])),
    "negate": (stack) => new Negate(...stack.splice(-numOfArgs["negate"], numOfArgs["negate"])),
    "min3": (stack) => new Min3(...stack.splice(-numOfArgs["min3"], numOfArgs["min3"])),
    "max5": (stack) => new Max5(...stack.splice(-numOfArgs["max5"], numOfArgs["max5"])),
    "sinh": (stack) => new Sinh(...stack.splice(-numOfArgs["sinh"], numOfArgs["sinh"])),
    "cosh": (stack) => new Cosh(...stack.splice(-numOfArgs["cosh"], numOfArgs["cosh"]))
};

const numOfArgs = {
    '+': 2,
    '-': 2,
    '*': 2,
    '/': 2,
    "negate": 1,
    "sinh": 1,
    "cosh": 1,
    "min3": 3,
    "max5": 5
};


function parse(string) {
    const array = string.split(" ").filter(el => el !== '');
    const stack = [];

    for (let i = 0; i < array.length; i++) {
        if (postEntry.hasOwnProperty(array[i])) {
            stack.push(postEntry[array[i]](stack));
        } else {
            stack.push(new Const(parseInt(array[i])))
        }
    }
    return stack.pop();
}

const vars = {'x': 'x', 'y': 'y', 'z': 'z'};

function parsePrefix(source) {
    let pos = 0;
    const eof = () => (pos > source.length - 1);
    const expected = (char) => char === curChar();
    const curChar = () => source[pos];
    const isWhiteSpace = () => curChar().trim() === '';
    const isBrackets = () => expected('(') || expected(')');
    const notStatedEl = () => undefined;

    function parseString() {
        skipWhiteSpaces();
        let begin = pos;
        while (!eof() && !isWhiteSpace() && !isBrackets()) {
            take();
        }
        const result = source.slice(begin, pos);
        if (result.length === 0) {
            throw new Error("There is no expression to parse");
        }
        return result;
    }

    function skipWhiteSpaces() {
        while (!eof() && isWhiteSpace()) {
            take();
        }
    }

    function take() {
        if (eof()) {
            throw new EOFError("Isn't expected end of parsing");
        }
        return source[pos++];
    }

    function test(char) {
        if (expected(char)) {
            take();
            return true;
        }
        return false;
    }

    const result = parseExpression();
    if (!eof()) {
        skipWhiteSpaces();
        if (!eof()) {
            throw new EOFError('Expected end of expression');
        }
    }
    return result;

    function parseExpression() {
        let el = '';
        skipWhiteSpaces();
        while (!eof()) {
            skipWhiteSpaces();

            if (test('(')) {
                return parseBracket();
            }

            el = parseString();
            skipWhiteSpaces();

            if (el in vars) {
                return parseVar(el);
            } else if (postEntry.hasOwnProperty(el)) {
                return parseOperations(el);
            } else {
                return parseNumber(el);
            }
        }

        throw new EOFError('Not expected end of parse');
    }

    function parseBracket() {
        const result = parseExpression();
        skipWhiteSpaces();
        if (result instanceof Variable || result instanceof Const) {
            throw new WrongExpressionError("Isn't expected single var or single number in brackets");
        }
        if (!test(')')) {
            throw new WrongBracketSequenceError('Expected close bracket');
        }
        return result;
    }

    function parseOperations(el) {
        const stack = [];
        for (let i = 0; i < numOfArgs[el]; i++) {
            stack.push(parseExpression());
            if (stack[i] === notStatedEl()) {
                throw new WrongExpressionError("Not enough arguments for expression");
            }
        }
        return postEntry[el](stack);
    }

    function parseNumber(el) {
        const num = Number(el);
        if (!isNaN(num)) {
            return new Const(num);
        } else {
            throw new WrongExpressionError("Wrong number in expression, str = " + el.toString());
        }
    }

    function parseVar(v) {
        return postEntry[v]();
    }
}