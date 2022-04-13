package expression.generic;

import expression.*;
import expression.exceptions.*;
import expression.parser.BaseParser;
import expression.parser.CharSource;
import expression.parser.StringCharSource;


public final class ExpressionParser<T extends Number> implements Parser<T> {

    public CommonExpression<T> parse(final CharSource source) throws EvaluatingException, ParserException {
        return new MPP(source).parse();
    }

    public CommonExpression<T> parse(final String string) throws EvaluatingException, ParserException {
        return parse(new StringCharSource(string));
    }

    private class MPP extends BaseParser {

        public MPP(CharSource source) {
            super(source);
        }

        private CommonExpression<T> parse() throws EvaluatingException, ParserException {
            if (take(')')) {
                throw new WIAException("Invalid input: found \")\", expected something other on the beginning of input");
            }
            skipWhitespace();
            final CommonExpression<T> result = parseMinMax();
            if (eof()) {
                return result;
            }
            if (take(')')) {
                throw new WBSException("Wrong bracket sequence, \"(\" expected");
            }
            throw new EOFPException("End of input expected");

        }

        private CommonExpression<T> parseMinMax() throws EvaluatingException, ParserException {
            CommonExpression<T> result = parseAS();
            while (!eof()) {
                skipWhitespace();
                if (take('m')) {
                    if (take('a')) {
                        if (take('x')) {
                            result = new Max<>(result, parseAS());
                        }
                    }
                    if (take('i')) {
                        if (take('n')) {
                            result = new Min<>(result, parseAS());
                        }
                    }
                } else {
                    break;
                }
            }

            return result;
        }

        private CommonExpression<T> parseAS() throws EvaluatingException, ParserException {
            CommonExpression<T> result = parseMD();
            while (!eof()) {
                skipWhitespace();
                if (take('+')) {
                    result = new Add<>(result, parseMD());
                } else if (take('-')) {
                    result = new Subtract<>(result, parseMD());
                } else {
                    break;
                }
            }

            return result;
        }

        private CommonExpression<T> parseMD() throws EvaluatingException, ParserException {
            CommonExpression<T> result = parseElements();
            while (!eof()) {
                skipWhitespace();
                if (take('*')) {
                    result = new Multiply<>(result, parseElements());
                } else if (take('/')) {
                    result = new Divide<>(result, parseElements());
                } else {
                    break;
                }
            }
            return result;
        }

        private CommonExpression<T> parseElements() throws EvaluatingException, ParserException {
            skipWhitespace();
            if (take('(')) {
                skipWhitespace();
                if (take(')')) {
                    throw new WBSException("Empty bracket sequence");
                }
                final CommonExpression<T> result = parseMinMax();
                skipWhitespace();
                if (take(')')) {
                    return result;
                }
                throw new WBSException("Wrong bracket sequence: \")\" expected");
            } else if (test('x') || test('y') || test('z')) {
                return parseVar();
            } else if (take('-')) {
                if (between('0', '9')) {
                    return parseConst(true);
                } else {
                    skipWhitespace();
                    return new Negate<>(parseElements());
                }
            } else if (take('c')) {
                if (take('o')) {
                    if (take('u')) {
                        if (take('n')) {
                            if (take('t')) {
                                return new CountsBits<>(parseElements());
                            }
                        }
                    }
                }

            } else if (between('0', '9')) {
                return parseConst(false);
            }
            throw new WIAException("Wrong input argument: " + getSource());

        }


        private void takeDigits(final StringBuilder sb) {
            while (between('0', '9')) {
                sb.append(take());
            }
        }

        private CommonExpression<T> parseVar() throws ParserException {
            if (take('x')) {
                return new Variable<>("x");
            } else if (take('y')) {
                return new Variable<>("y");
            } else if (take('z')) {
                return new Variable<>("z");
            }
            throw new VariableParseException("Expected variable," + getCurChar() + "found");
        }


        private CommonExpression<T> parseConst(boolean isMinus) throws EvaluatingException, ParserException {
            final StringBuilder sb = isMinus ? new StringBuilder("-") : new StringBuilder();

            if (take('0')) {
                sb.append('0');
            } else if (between('1', '9')) {
                takeDigits(sb);
            } else {
                throw new NumberParseException("Invalid number");
            }

            if (take('.')) {
                sb.append(".");
                if (between('0', '9')) {
                    takeDigits(sb);
                } else {
                    throw new NumberParseException("Invalid number");
                }
            }

            if (getCurChar() == 'm') {
                throw new WIAException("Wrong number parse");
            }

            return new Const<>(sb.toString());
        }


        private void skipWhitespace() {
            while (Character.isWhitespace(getCurChar())) {
                take();
                // skip
            }
        }
    }

}
