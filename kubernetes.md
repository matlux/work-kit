

## How to Provide Docker Hub Credentials to Kubernetes

### Steps to Provide Docker Hub Credentials to Kubernetes

1. **Create a Kubernetes Secret with Your Docker Hub Credentials**:

   You need to create a secret in Kubernetes that contains your Docker Hub credentials. You can use the `kubectl create secret docker-registry` command to do this.

   ```sh
   kubectl create secret docker-registry mysecret \
     --docker-server=https://index.docker.io/v1/ \
     --docker-username=<your-dockerhub-username> \
     --docker-password=<your-dockerhub-password> \
     --docker-email=<your-email>
   ```

   Replace `<your-dockerhub-username>`, `<your-dockerhub-password>`, and `<your-email>` with your actual Docker Hub credentials.

2. **Update Your Deployment YAML to Use the Secret**:

   In your `deployment.yaml` file, specify the secret in the `imagePullSecrets` section so that Kubernetes knows to use these credentials when pulling the image.

   ```yaml
   apiVersion: apps/v1
   kind: Deployment
   metadata:
     name: utc-testchain-deployment
   spec:
     replicas: 4
     selector:
       matchLabels:
         app: utc-testchain
     template:
       metadata:
         labels:
           app: utc-testchain
       spec:
         containers:
         - name: utctestchain
           image: <your-dockerhub-username>/yourimage:yourtag
           ports:
           - containerPort: 80
         imagePullSecrets:
         - name: mysecret
   ```

3. **Apply the Updated Deployment**:

   Apply your updated `deployment.yaml` file to the cluster.

   ```sh
   kubectl apply -f deployment.yaml
   ```

4. **Verify the Pods**:

   Check the status of the Pods to ensure they are running correctly.

   ```sh
   kubectl get pods -o wide
   ```

### Conclusion

By creating a Kubernetes secret with your Docker Hub credentials and referencing this secret in your deployment configuration, you ensure that the nodes in your Kubernetes cluster can authenticate to Docker Hub and pull images from your private repositories. This setup should resolve the `ImagePullBackOff` issue for your deployment.



## How to scale down a container 

### Option 1: Scale Down the Deployment

You can scale down the deployment to zero replicas, which will stop all the pods managed by that deployment.

1. **Scale Down the Deployment**:

   ```sh
   kubectl scale deployment utc-testchain-deployment --replicas=0
   ```

2. **Verify the Pods**:

   Ensure that all pods have been terminated.

   ```sh
   kubectl get pods
   ```

## How to stop the container 

### Option 2: Delete the Deployment

If you want to completely remove the deployment, you can delete it.

1. **Delete the Deployment**:

   ```sh
   kubectl delete deployment utc-testchain-deployment
   ```

2. **Verify the Pods**:

   Ensure that all pods associated with the deployment have been terminated.

   ```sh
   kubectl get pods -o wide
   ```


## How to exec into a container

```sh
kubectl exec -it utc-testchain-deployment-mathieu-7c9d84f4fc-qvmcs -- /bin/sh
```


## How to follow logs on container

```sh
kubectl logs utc-testchain-deployment-mathieu-7c9d84f4fc-qvmcs -f
```


## How to find out the public IP address of your container, you need to look at the Kubernetes service that exposes your container to the outside world. In Kubernetes, pods themselves do not have public IP addresses; instead, services are used to expose pods.

### Steps to Find the Public IP Address

1. **Check the Service**:
   Ensure that there is a Kubernetes service that exposes your deployment. Typically, services of type `LoadBalancer` or `NodePort` are used to expose containers to the outside world.

2. **Get Service Information**:
   Use `kubectl get services` to list all services and find the service exposing your container.

3. **Describe the Service**:
   Use `kubectl describe service <service-name>` to get detailed information about the service, including the public IP address (if any).

### Example Commands

#### Step 1: List All Services

```sh
kubectl get services
```

This will output something like:

```
NAME                        TYPE           CLUSTER-IP      EXTERNAL-IP     PORT(S)           AGE
kubernetes                  ClusterIP      10.0.0.1        <none>          443/TCP           10d
utc-testchain-service       LoadBalancer   10.0.0.2        35.236.5.50     80:30123/TCP      1d
```

#### Step 2: Describe the Service

Assuming your service name is `utc-testchain-service`:

```sh
kubectl describe service utc-testchain-service
```

This will provide detailed information about the service, including the `EXTERNAL-IP` field for `LoadBalancer` services or the node ports for `NodePort` services.

### Example Output of `kubectl describe service`

```
Name:                     utc-testchain-service
Namespace:                default
Labels:                   app=utc-testchain
Annotations:              <none>
Selector:                 app=utc-testchain
Type:                     LoadBalancer
IP:                       10.0.0.2
LoadBalancer Ingress:     35.236.5.50
Port:                     <unset>  80/TCP
TargetPort:               80/TCP
NodePort:                 <unset>  30123/TCP
Endpoints:                10.2.0.19:80, 10.2.1.11:80
Session Affinity:         None
Events:                   <none>
```

- **`LoadBalancer Ingress`**: Shows the public IP address assigned by the cloud provider (e.g., 35.236.5.50).
- **`NodePort`**: If using a NodePort service, you can access the service via any node’s IP address and the node port (e.g., 30123).

### Conclusion

To find the public IP address of your container:

1. Ensure your deployment is exposed via a service.
2. Use `kubectl get services` to list services and identify the relevant one.
3. Use `kubectl describe service <service-name>` to find the public IP address.

By following these steps, you can locate the public IP address assigned to your service, allowing external access to your container.




## How to setup a service for a new container



To expose your new container running in the pod `utc-testchain-deployment-mathieu-7c9d84f4fc-qvmcs` with a Kubernetes service without affecting the other services, you can create a separate `service.yaml` file. This service will target the new pod and expose its ports.

Here is the `service.yaml` file you can use:

```yaml
apiVersion: v1
kind: Service
metadata:
  name: utc-testchain-service-mathieu-26657
spec:
  selector:
    app: utc-testchain-mathieu
  ports:
    - protocol: TCP
      port: 26657
      targetPort: 26657
      nodePort: 30057
  type: NodePort

---
apiVersion: v1
kind: Service
metadata:
  name: utc-testchain-service-mathieu-26656
spec:
  selector:
    app: utc-testchain-mathieu
  ports:
    - protocol: TCP
      port: 26656
      targetPort: 26656
      nodePort: 30056
  type: NodePort

---
apiVersion: v1
kind: Service
metadata:
  name: utc-testchain-service-mathieu-1317
spec:
  selector:
    app: utc-testchain-mathieu
  ports:
    - protocol: TCP
      port: 1317
      targetPort: 1317
      nodePort: 3117
  type: NodePort

---
apiVersion: v1
kind: Service
metadata:
  name: utc-testchain-service-mathieu-4500
spec:
  selector:
    app: utc-testchain-mathieu
  ports:
    - protocol: TCP
      port: 4500
      targetPort: 4500
      nodePort: 31450
  type: NodePort
```

### Applying the Configuration

1. **Create the Service Configuration File**:
   Save the above configuration into a file named `service-mathieu.yaml`.

2. **Apply the Service Configuration**:
   Apply the service configuration using `kubectl`:

   ```sh
   kubectl apply -f service-mathieu.yaml
   ```

3. **Verify the Services**:
   Check the services to ensure they have been created correctly:

   ```sh
   kubectl get services
   ```

### Ensure Correct Labeling

Make sure that your deployment or pod has the correct label that the services are selecting. Your deployment should have a label `app: utc-testchain-mathieu`. Here’s a quick example of what your deployment should look like:

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: utc-testchain-deployment-mathieu
spec:
  replicas: 1
  selector:
    matchLabels:
      app: utc-testchain-mathieu
  template:
    metadata:
      labels:
        app: utc-testchain-mathieu
    spec:
      containers:
      - name: utctestchain
        image: matlux/2060:v1
        imagePullPolicy: IfNotPresent
        ports:
        - containerPort: 26657
        - containerPort: 26656
        - containerPort: 1317
        - containerPort: 4500
        command: ["utctestchaind", "start"]
```

### Conclusion

This setup will create separate services for your new deployment, exposing it on the specified node ports. This allows you to access the new deployment's services without interfering with the existing services. By applying the configuration and verifying the services, you ensure that your new pod is accessible as needed.




-----

## How do I exec into the veranalabs/faucet-aap:latest of this stateful set  ?

To exec into the veranalabs/faucet-app:latest container from your StatefulSet (verana-faucet-hologram-chatbot), follow these steps:

Step 1: Get the Pod name

Since this is a StatefulSet with one pod (1/1), the pod name is likely:

```
verana-faucet-hologram-chatbot-0
```

You can confirm this by running:

```
kubectl get pods -n vna-testnet-1
```

Look for the pod that starts with verana-faucet-hologram-chatbot.

⸻

Step 2: Find the Container Name (Optional)

If there are multiple containers (based on the image list, it looks like there are), check which one you want to exec into:

```
kubectl describe pod verana-faucet-hologram-chatbot-0 -n vna-testnet-1
```

This will list all container names. Look for the one using the image veranalabs/faucet-app:latest.

⸻

Step 3: Exec into the Container

Now you can exec into the container. If it’s named faucet-app (adjust if different), run:

```
kubectl exec -it verana-faucet-hologram-chatbot-0 -n vna-testnet-1 -c faucet-app -- /bin/sh
```


## How to list the deployement config

```
kubectl get deployment verana-testnet-frontend-app-deployment -n vna-testnet-1 -o yaml
```

## How to create a dashboard token

```
kubectl -n kubernetes-dashboard create token admin-user
```


## How to see the config of an ingress?

```
kubectl -n vna-testnet-1 describe ingress verana-testnet-frontend-ingress
```

or

```
kubectl -n vna-testnet-1 get ingress verana-testnet-frontend-ingress -o yaml
```