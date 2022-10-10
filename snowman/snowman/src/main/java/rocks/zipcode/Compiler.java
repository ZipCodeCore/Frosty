package rocks.zipcode;

import java.util.ArrayList;
import java.util.Iterator;

public class Compiler {

    enum TokenType {
        paren,
        thesis,
        name,
        number,
        string
    }
    class Token {
        public TokenType type;
        public String value;
        Token(TokenType type, String value) {
            this.type = type;
            this.value = value;
        }
    }

    enum AstType {
        program,
        callexpression,
        numberliteral,
        stringliteral
    }
    class Ast {
        public AstType type;
        public String value;
        public ArrayList<Ast> params;
        Ast(AstType type,
            String value) {
                this.type = type;
                this.value = value;
                this.params = new ArrayList<>();
            }
    }

    public String compile(String input) {
        ArrayList<Token> tokens;
        try {
            tokens = tokenizer(input);
            printTokens(tokens);
            Ast ast = parser(tokens);
            //NewAst newAst = transformer(ast);
            String output = codeGenerator(ast);
            return output;
            } catch (Exception e) {
                System.err.println("Error in input: "+e);
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<Token> tokenizer(String input) throws Exception {
        int current = 0;
        ArrayList<Token> tokens = new ArrayList<>();
        // maybe this should be a weird Iterable??? with a push()??

        while (current < input.length()) {

            String ch = String.valueOf(input.charAt(current));

            if (ch.equals("(")) {
                tokens.add(new Token(TokenType.paren, "("));
                current++;
                continue;
            }
            if (ch.equals(")")) {
                tokens.add(new Token(TokenType.thesis, ")"));
                current++;
                continue;
            }
            if (ch.isBlank()) {
                current++;
                continue;
            }
            if (ch.matches("[0-9]")) {
                String value = "";
                while (ch.matches("[0-9]")) {
                    value = value + ch;
                    current++;
                    ch = String.valueOf(input.charAt(current));
                }
                tokens.add(new Token(TokenType.number, value));
                continue;
            }
            if (ch.equals("\"")) {
                String value = "";
                while (!ch.equals("\"")) { // NB !
                    value = value + ch;
                    current++;
                    ch = String.valueOf(input.charAt(current));
                }
                ch = String.valueOf(input.charAt(current));
                tokens.add(new Token(TokenType.string, value));
                continue;
            }
            if (ch.matches("[a-zA-Z]")) {
                String value = "";
                while (ch.matches("[a-zA-Z]")) {
                    value = value + ch;
                    current++;
                    ch = String.valueOf(input.charAt(current));
                }
                tokens.add(new Token(TokenType.name, value));
                continue;
            }
            throw new Exception("Illegal character in input.");
        }
        return tokens;
    }
    
    private void printTokens(ArrayList<Token> tts) {
        for (Token t : tts) {
            System.out.println(t.type+":"+t.value);
        }
    }
    private Ast parser(ArrayList<Token> tokens) {
        //int current = 0;
        Ast root = new Ast(AstType.program, null);
        Iterator<Token> tokenIterator = tokens.iterator();
        Token token = null;
        while (tokenIterator.hasNext()) {
            root.params.add(this.walk(token, tokenIterator));
        }
        return root;
    }

    private Ast walk(Token token, Iterator<Token> tokens) {
        if (tokens.hasNext()) {
            token = tokens.next();
            System.err.println("walk: 0 "+token.value);
        } else System.err.println("EOF 0");

        //= tokens.get(idx);

        if (token.type == TokenType.number) {
            return new Ast(AstType.numberliteral, token.value);
        }
        if (token.type == TokenType.string) {
            return new Ast(AstType.stringliteral, token.value);
        }
        if (token.type == TokenType.paren) {
            if (tokens.hasNext()) {
                token = tokens.next();
                System.err.println("walk: 1 "+token.value);
            } else System.err.println("EOF 1");
            Ast node = new Ast(AstType.callexpression, token.value);

            while (token.type != TokenType.thesis) {
                Ast t = walk(token, tokens);
                if (t != null) node.params.add(t);
                else break;
            }
            return node;
        }
        if (token.type == TokenType.thesis) {
            return null;
        }

        System.err.println("UNKNOWN TOKEN..."+token.value);
        return null;
    }


    private String codeGenerator(Ast ast) {
        System.out.println(">>BEGIN");
        printNode(ast);
        System.out.println(">>END");
        return null;
    }

    private void printNode(Ast ast) {
        System.out.println(ast.type.toString()+":"+ast.value);
        for (Ast e : ast.params) {
            printNode(e);
        }
    }
}
