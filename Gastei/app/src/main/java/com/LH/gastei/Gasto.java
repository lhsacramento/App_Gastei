package com.LH.gastei;

public class Gasto
{
    private String usedLogin,dataCompra,generoDespesas,formaPagamento,classificacaoGasto,descreverGasto, valorGasto;

    public String getDataCompra() {
        return dataCompra;
    }

    public String getUsedLogin() {
        return usedLogin;
    }

    public void setUsedLogin(String usedLogin) {
        this.usedLogin = usedLogin;
    }

    public void setDataCompra(String dataCompra) {
        this.dataCompra = dataCompra;
    }

    public String getGeneroDespesas() {
        return generoDespesas;
    }

    public void setGeneroDespesas(String generoDespesas) {
        this.generoDespesas = generoDespesas;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public String getClassificacaoGasto() {
        return classificacaoGasto;
    }

    public void setClassificacaoGasto(String classificacaoGasto) {
        this.classificacaoGasto = classificacaoGasto;
    }

    public String getDescreverGasto() {
        return descreverGasto;
    }

    public void setDescreverGasto(String descreverGasto) {
        this.descreverGasto = descreverGasto;
    }

    public String getValorGasto() {
        return valorGasto;
    }

    public void setValorGasto(String valorGasto) {
        this.valorGasto = valorGasto;
    }
}
