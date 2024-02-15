# The Java Super Tiny Compiler.

This is a java implementation of the-super-tiny-compiler written in javascript by @jamiebuilds

Tranliterated into Java by @xt0fer for zip code wilmington lab on compiler phases and the ideas of scanning and parser into trees (well, ASTs).

It also has only three phases, not four like in the original. 

The first phase is the tokenizer, the second is the parser, and the  third is code generation.

Right now the `language` is very simple.

It can add or subtract integers.

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

### What Do I Do?

- now, you add `multiply` and `divide` as two new operations. DO you need to do anything at all? 
Explain WHY in a comment at the correct point in the code
- how about comments like "// comments..." (from // to end of line)
- you would add comments a little like how you ignore whitespace in the Tokenizer(scanner/lexer)

### No seriously, now what?

When the program generates text output, it looks a bit like this:

```
;; Begin program code
		START
;; call enter
;; num enter
		PUSH #2
;; call enter
;; num enter
		PUSH #4
;; num enter
		PUSH #2
		DO SUBTRACT
		DO ADD
;; Print top of stack
		POP
		PRINT
		HALT
```

If you put that into a file, say `testdata.zas`, write a program to "run" the file and compute the result.

What would you have to do?

```java
class ZasRunner {
    
    // in the runFile(filename) method you would
    
    //open the file
    //if it's a comment line read to end of line
    // else interpret the line
    
    
    // in interpretLine(line)
    // break into opcode and argument
    // switch (opcode)
    // case ADD
    //   add two number at top of operands stack and leave result at top of stack
    // etc... for other math ops
    // case PUSH...
    // 
    //
    //
    // case PRINT
    //   print the number at top of stack to standard output
    // case HALT
    //    quit program
    
    // need a OPERAND stack to put numbers on
    // stack needs to be able to push and pop, (maybe peek)
    
    // that wasn't that hard, was it?
}
```

### Multiply and Divide

Now, what do you need to do to add `multiply` and `divide`?

### Doubles

What about floating point numbers (well, doubles) like `(multiply (add 5.6 2.1) (multiply 4.352 7.832))`?
How much work is adding that? How long did it take?

(Yeah, these days seems like its all done in `double`s. Someday, you'll have to explain why a floating point number is called
a double, and you'll feel old and start talking about when computers only had 32bit registers, and java... blah
blah, and you'll feel old and remember me.)

## Ideas

As you build the Virtual Machine described above, what might you have to add to it to support some of these ideas?

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
(IF (LT 4 5) (add 4 5))
```

and what about ?

```
(LAMBDA (X Y) (...))
(λ (X Y) (...))
;; yeah, that really is a lambda in the second example
```

I fully expect that last one to break your brain. 
If not, send your resume to Google, they'll want to talk to you.