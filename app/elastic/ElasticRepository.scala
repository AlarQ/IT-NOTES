package elastic

import com.sksamuel.elastic4s.http.JavaClient
import com.sksamuel.elastic4s.{ElasticClient, ElasticProperties}
import elastic.ops.{ReadOps, WriteOps}
import org.apache.http.auth.{AuthScope, UsernamePasswordCredentials}
import org.apache.http.client.config.RequestConfig
import org.apache.http.impl.client.BasicCredentialsProvider
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback

import scala.concurrent.ExecutionContext

class ElasticRepository(props: ElasticProperties) extends WriteOps with ReadOps {
  implicit val ec: ExecutionContext = ExecutionContext.global
  lazy val provider = {
    val provider = new BasicCredentialsProvider
    val credentials = new UsernamePasswordCredentials("ip18nxx5a9", "uqk3oeu05g")
    provider.setCredentials(AuthScope.ANY, credentials)
    provider
  }

  implicit val elasticClient = ElasticClient(JavaClient(
    ElasticProperties(s"http://localhost:9200")
  ))

  implicit val elasticClient1 = ElasticClient(JavaClient(ElasticProperties("https://alder-477352390.us-east-1.bonsaisearch.net:443"), (requestConfigBuilder: RequestConfig.Builder) => requestConfigBuilder, new HttpClientConfigCallback {
    override def customizeHttpClient(httpClientBuilder: HttpAsyncClientBuilder) = {
      httpClientBuilder.setDefaultCredentialsProvider(provider)
    }
  }))

}

