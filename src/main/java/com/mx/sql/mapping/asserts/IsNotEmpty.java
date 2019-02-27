package com.mx.sql.mapping.asserts;


import com.mx.sql.dialect.IDialect;
import com.mx.sql.mapping.ISqlClauseAssert;

public class IsNotEmpty implements ISqlClauseAssert {
    @Override
    public String getName() {
        return "isNotEmpty";
    }

    @Override
    public boolean invoke(IDialect dialect,Object... args) {
        if(args.length==0)return true;
        for(int i=0;i<args.length;i++){
            Object arg = args[i];
            if(arg!=null && !"".equals(arg)){
                return true;
            }
        }
        return false;
    }
}
