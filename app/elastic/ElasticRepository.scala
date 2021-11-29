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

case class ElasticRepository(props: ElasticProperties) extends WriteOps with ReadOps {
  implicit val ec: ExecutionContext = ExecutionContext.global

  lazy val provider = {
    val provider = new BasicCredentialsProvider
    val userName = sys.env("ES_USER")
    val pass = sys.env("ES_PASS")
    val credentials = new UsernamePasswordCredentials(userName, pass)
    provider.setCredentials(AuthScope.ANY, credentials)
    provider
  }

  implicit val elasticClient = {
    val esHost = sys.env("ES_HOST")
    ElasticClient(
      JavaClient(
        ElasticProperties(esHost),
        (requestConfigBuilder: RequestConfig.Builder) => requestConfigBuilder,
        new HttpClientConfigCallback {
          override def customizeHttpClient(httpClientBuilder: HttpAsyncClientBuilder) = {
            httpClientBuilder.setDefaultCredentialsProvider(provider)
          }
        }
      )
    )
  }
}
