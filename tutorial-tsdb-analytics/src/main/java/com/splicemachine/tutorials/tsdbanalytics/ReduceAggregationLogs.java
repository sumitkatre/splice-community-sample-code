/*
 * Copyright 2012 - 2016 Splice Machine, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the
 * License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.splicemachine.tutorials.tsdbanalytics;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import org.apache.spark.api.java.function.Function2;

import com.splicemachine.tutorials.tsdbanalytics.dataobjects.AggregationLog;
/**
 * This performs aggregation. It accepts 2 AggregationLog Objects and combines them. 
 * and returns AggregationResult 
 * @author Jyotsna Ramineni
 *
 */

public class ReduceAggregationLogs implements Function2<AggregationLog,AggregationLog,AggregationLog>, Externalizable{



@Override
public AggregationLog call(AggregationLog agg1, AggregationLog agg2)
		throws Exception {
	
	AggregationLog retAgg = new AggregationLog(Math.min(agg1.getTimestamp(), agg2.getTimestamp()),
			agg1.getSumBids() + agg2.getSumBids(),
			agg1.getImps() + agg2.getImps(),
			agg1.getUniquesHll().$plus(agg2.getUniquesHll())
			);
	agg1.setTimestamp(retAgg.getTimestamp());
	agg1.setSumBids(retAgg.getSumBids());
	agg1.setImps(retAgg.getImps());
	agg1.setUniquesHll(retAgg.getUniquesHll());
	return agg1;
}

@Override
public void writeExternal(ObjectOutput out) throws IOException {
	// TODO Auto-generated method stub
	
}

@Override
public void readExternal(ObjectInput in) throws IOException,
		ClassNotFoundException {
	// TODO Auto-generated method stub
	
}

}