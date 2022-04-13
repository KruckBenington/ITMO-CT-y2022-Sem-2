"use strict";

let cnst = a => () => a;
let variable = (v) => (x, y, z) => v === "x" ? x : v === "y" ? y : z;

let binaryOperation = (func, firstExp, secondExp) => (x, y, z) => func(firstExp(x, y, z), secondExp(x, y, z));
let add = (a, b) => binaryOperation((a, b) => a + b, a, b);
let subtract = (a, b) => binaryOperation((a, b) => a - b, a, b);
let multiply = (a, b) => binaryOperation((a, b) => a * b, a, b);
let divide = (a, b) => binaryOperation((a, b) => a / b, a, b);

let unaryOperation = (func, exp) => (x, y, z) => func(exp(x, y, z));
let negate = (a) => unaryOperation((a) => -a, a);

let pi = cnst(Math.PI);
let e = cnst(Math.E);


/*
Testing part of program;

let expr = add(subtract(multiply(variable("x"), variable("x")), multiply(cnst(2), variable("x"))), cnst(1));
for (let i = 0; i < 10; i++) {
    console.log(expr(i, 0, 0));
}*/
