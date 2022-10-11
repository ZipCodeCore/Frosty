# The Java Super Tiny Compiler.

This is a java implementation of the-super-tiny-compiler written in javascript by @jamiebuilds

Tranliterated into Java by @xt0fer for zip code wilmington lab on compiler phases and the ideas of scanning and parser into trees (well, ASTs).

It also has only three phases, not four like in the original. 

The first phase is the tokenizer, the second is the parser, and the  third is code generation.

Right now the `language` is very simple.

It allows for add or subtract integers.

```
(add 2 (subtract 4 2)) 
```

produces a pseudo-assembly-code of 

```
;; Begin program code
                START
                PUSH #2
                PUSH #4
                PUSH #2
                DO SUBTRACT
                DO ADD
                POP
                PRINT
                HALT
```

- now, you add `multiply` and `divide`
- how about comments like "// comments..." (from // to EOL)


## Ideas

### Variables
Integer values mapped to names.
```
(LET X 5)
(LET Y (add 5 8))
(add x y)
```

Could then
```
(LET TRUE 1)
(LET FALSE 0)
```

might also 

```
(LT 4 5) -> 1
(GE 4 5) -> 0
(GE 3 3) -> 1
(GE 5 4) -> 1
(EQ 5 5) -> 1
(NE 4 5) -> 1
(NE 3 3) -> 1
(EQ 1 x) -> isTrue
(EQ 0 x) -> isFalse
```

and also
```
(IF 1 (add 4 5))
```

and what about 

```
(LAMBDA (X Y) (...))
(λ (X Y) (...))

```