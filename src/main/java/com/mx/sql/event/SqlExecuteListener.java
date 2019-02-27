package com.mx.sql.event;

import java.util.EventListener;

public interface SqlExecuteListener extends EventListener {
	void onSqlExecute();
}
