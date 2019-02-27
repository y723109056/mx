package com.mx.sql.mapping.asserts;

import com.mx.sql.dialect.IDialect;
import com.mx.sql.mapping.ISqlClauseAssert;

public class IsDbType implements ISqlClauseAssert {

    @Override
    public String getName() {
        return "isDbType";
    }

    @Override
    public boolean invoke(IDialect dialect,Object... args) {
        if(args.length>0){
            if(args[0]!=null){
                return args[0].equals(dialect.getDbType());
            }
        }
        return false;
    }
}
