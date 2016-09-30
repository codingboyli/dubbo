/*
 * Copyright 1999-2012 Alibaba Group.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.alibaba.dubbo.rpc.filter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicBoolean;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;

/**
 * 
 *
 */
@Activate(group = Constants.PROVIDER)
public class ConnectionFilter implements Filter {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionFilter.class);

	AtomicBoolean flag = new AtomicBoolean(true);
 
	Method method = null;

	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		try {
			Result result = invoker.invoke(invocation);
			return result;
		} finally {
			if (flag.get()) {
				if (  method == null) {
					try {
						Class<?> cls = Class.forName("com.yhfund.dc.pool.DBManager");
						method = cls.getMethod("closeAllConnection");
						method.invoke(null);
						LOGGER.info("auto close db connection");	
					} catch (Exception e) {
						flag.set(false);
						LOGGER.info("cannot find DBManager class");	
					}
				} else {
					if (flag.get() &&  method != null) {
						try {
							method.invoke(null);
							LOGGER.info("auto close db connection");	
						} catch (IllegalAccessException e) {
							LOGGER.error("error", e);
						} catch (IllegalArgumentException e) {
							LOGGER.error("error", e);
						} catch (InvocationTargetException e) {
							LOGGER.error("error", e);
						}
					}
				}
			}
		}

	}
}
