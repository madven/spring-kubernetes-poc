if you get:
```
WARN [-,,,] 1 --- [           main] o.s.cloud.kubernetes.StandardPodUtils    : Failed to get pod with name:[greeting-service-deployment-v1-54d89f7d89-s7qab]. You should look into this if things aren't working as you expect. Are you missing serviceaccount permissions?

io.fabric8.kubernetes.client.KubernetesClientException: Failure executing: GET at: https://10.51.230.1/api/v1/namespaces/default/pods/greeting-service-deployment-v1-54d89f7d89-s7qab. Message: Forbidden!Configured service account doesn't have access. Service account may have been revoked. pods "greeting-service-deployment-v1-54d89f7d89-s7qab" is forbidden: User "system:serviceaccount:default:default" cannot get resource "pods" in API group "" in the namespace "default".
```
exception, then:

https://kubernetes.io/docs/reference/access-authn-authz/rbac/

Grant super-user access to all service accounts cluster-wide (strongly discouraged)

If you don’t care about partitioning permissions at all, you can grant super-user access to all service accounts.

Warning: This allows any user with read access to secrets or the ability to create a pod to access super-user credentials.
```bash
kubectl create clusterrolebinding serviceaccounts-cluster-admin \
  --clusterrole=cluster-admin \
  --group=system:serviceaccounts
```

For kubernetes: 
(edit docker image in runner and workload)
`./runner.sh -gn`

For istio: 
(edit docker image in runner and workload)
(edit greeting-service in workload from LoadBalancer to NodePort)
`./runner.sh -gni`