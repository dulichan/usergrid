package org.apache.usergrid.persistence.collection.rx;


import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.netflix.astyanax.Execution;
import com.netflix.astyanax.connectionpool.OperationResult;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.subscriptions.Subscriptions;
import rx.util.functions.Func1;


/** @author tnine */
public class Astyanax {


    public static <T, R> Observable<R> insertMutation(final Execution<T> ex, final R val){
        return executeAsync( ex ).map( new Func1<OperationResult<T>, R>() {
            @Override
            public R call( final OperationResult<T> tOperationResult ) {
                return val;  //To change body of implemented methods use File | Settings | File Templates.
            }
        } );

    }

    public static <R> Observable<OperationResult<R>> executeAsync( final Execution<R> execution ) {
        return Observable.create( new Observable.OnSubscribeFunc<OperationResult<R>>() {

            @Override
            public Subscription onSubscribe( final Observer<? super OperationResult<R>> observer ) {
                try {
                    Futures.addCallback( execution.executeAsync(), new FutureCallback<OperationResult<R>>() {

                        @Override
                        public void onSuccess( OperationResult<R> result ) {
                            observer.onNext( result );
                            observer.onCompleted();
                        }


                        @Override
                        public void onFailure( Throwable t ) {
                            observer.onError( t );
                        }
                    } );
                }
                catch ( ConnectionException e ) {
                    observer.onError( e );
                }
                catch ( Throwable e ) {
                    // If other Throwable can be thrown from execution.executeAsync
                    observer.onError( e );
                }
                return Subscriptions.empty();
            }
        } );
    }
}
