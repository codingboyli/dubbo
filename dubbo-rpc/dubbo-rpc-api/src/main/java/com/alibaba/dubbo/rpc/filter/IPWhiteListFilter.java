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

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.RpcResult;

/**
 * 白名单过滤
 *
 */
@Activate(group = Constants.PROVIDER)
public class IPWhiteListFilter implements Filter {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(IPWhiteListFilter.class);
	
	public Result invoke(Invoker<?> invoker, Invocation invocation)
			throws RpcException {
		String ipfilter = invoker.getUrl().getParameter("ipfilter","false");
		if (!"true".equals(ipfilter)) {
			LOGGER.debug("白名单禁用");  
			return invoker.invoke(invocation);
		}
		String clientIp = RpcContext.getContext().getRemoteHost();  
		LOGGER.debug("访问ip为"+clientIp);
		String ips = invoker.getUrl().getParameter("whitelist","");
		if(ips.contains(clientIp)){
			return invoker.invoke(invocation);
		}else{
			return new RpcResult();  
		}
	}
}
