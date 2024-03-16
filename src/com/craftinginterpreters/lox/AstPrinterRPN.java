package com.craftinginterpreters.lox;

public class AstPrinterRPN implements Expr.Visitor<String> {

    public static void main(String[] args) {
        Expr expression = new Expr.Binary(
                new Expr.Binary(
                        new Expr.Grouping(new Expr.Literal(1)),
                        new Token(TokenType.PLUS, "+", null, 1),
                        new Expr.Grouping(
                                new Expr.Literal(2))
                ),
                new Token(TokenType.STAR, "*", null, 1),
                new Expr.Binary(
                        new Expr.Grouping(new Expr.Literal(4)),
                        new Token(TokenType.MINUS, "-", null, 1),
                        new Expr.Grouping(
                                new Expr.Literal(3)
                        )
                )
        );


        System.out.println(new AstPrinterRPN().print(expression));
    }


    String print(Expr expr) {
        return expr.accept(this);
    }

    @Override
    public String visitAssignExpr(Expr.Assign expr) {
        return null;
    }

    @Override
    public String visitBinaryExpr(Expr.Binary expr) {
        return expr.left.accept(this) + " " + expr.right.accept(this) + " " + expr.operator.lexeme;
    }

    @Override
    public String visitGroupingExpr(Expr.Grouping expr) {
        return expr.expression.accept(this);
    }

    @Override
    public String visitLiteralExpr(Expr.Literal expr) {
        if (expr.value == null) {
            return "nil";
        }
        return expr.value.toString();
    }

    @Override
    public String visitUnaryExpr(Expr.Unary expr) {
        String operator = expr.operator.lexeme;
        if (expr.operator.type == TokenType.MINUS) {
            operator = "neg ";
        }
        return expr.right.accept(this) + " " + operator;
    }

    @Override
    public String visitTernaryExpr(Expr.Ternary expr) {
        return null; //i'm too lazy to implement this for now
    }

    @Override
    public String visitVariableExpr(Expr.Variable expr) {
        return null;
    }
}
