# CI/CD Pipeline Configuration Guide

This repository includes two GitHub Actions workflows for different needs:

## 🚀 Available Workflows

### 1. **Simple CI/CD Pipeline** (`simple-ci.yml`)
- ✅ Unit tests with Maven
- ✅ Docker build and containerization  
- ✅ Cypress E2E testing
- ✅ Artifact upload (screenshots, videos, Docker images)
- ❌ No security scanning (faster, no API key needed)

### 2. **Full CI/CD Pipeline** (`ci-cd.yml`)
- ✅ Everything from Simple CI/CD
- ✅ Advanced security scanning with Trivy
- ✅ Vulnerability reporting
- ⚠️ Requires NVD API key for optimal performance

## 🔧 Choosing the Right Workflow

### For Development/Learning Projects:
Use `simple-ci.yml` - rename it to `ci-cd.yml` and delete the full version:

```bash
mv .github/workflows/simple-ci.yml .github/workflows/ci-cd.yml
rm .github/workflows/ci-cd.yml  # Remove the full version
```

### For Production Projects:
Keep the full `ci-cd.yml` and optionally add an NVD API key.

## 🔑 Setting up NVD API Key (Optional but Recommended)

The warning about NVD API Key occurs because vulnerability database updates are much faster with an API key.

### Step 1: Get an NVD API Key
1. Go to [National Vulnerability Database](https://nvd.nist.gov/developers/request-an-api-key)
2. Request a free API key
3. Wait for email confirmation (usually within a few hours)

### Step 2: Add API Key to GitHub Secrets
1. In your GitHub repository, go to **Settings > Secrets and variables > Actions**
2. Click **New repository secret**
3. Name: `NVD_API_KEY`
4. Value: Your API key from step 1
5. Click **Add secret**

### Step 3: Activate the API Key
In `.github/workflows/ci-cd.yml`, uncomment this line:
```yaml
# TRIVY_NVD_API_KEY: ${{ secrets.NVD_API_KEY }}
```

Change it to:
```yaml
TRIVY_NVD_API_KEY: ${{ secrets.NVD_API_KEY }}
```

## ⚡ Workflow Performance Optimization

### Without NVD API Key:
- Security scan: ~10-15 minutes
- Total pipeline: ~20-25 minutes

### With NVD API Key:
- Security scan: ~2-3 minutes  
- Total pipeline: ~10-15 minutes

### Using Simple CI/CD:
- No security scan
- Total pipeline: ~8-12 minutes

## 🛠️ Manual Security Scanning

If you prefer to run security scans manually instead of in CI/CD:

```bash
# Install Trivy locally
docker run --rm -v $(pwd):/workspace aquasecurity/trivy image biztrips-app:latest

# Or scan with specific severity
docker run --rm -v $(pwd):/workspace aquasecurity/trivy image --severity HIGH,CRITICAL biztrips-app:latest
```

## 📊 Understanding the Security Scan

The Trivy scanner checks for:
- ✅ Known vulnerabilities in base images
- ✅ Vulnerable dependencies in your application
- ✅ Misconfigurations in Docker setup
- ✅ Secret exposure in code

Results are uploaded to:
- GitHub Security tab (for security alerts)
- Workflow artifacts (for download)

## 🔄 Alternative: Disable Security Scanning

To completely disable security scanning in the full pipeline, simply comment out or remove the `security-scan` job:

```yaml
# security-scan:
#   needs: build
#   runs-on: ubuntu-latest
#   ... (entire job)
```

## 🎯 Recommended Setup for Different Scenarios

| Scenario | Recommended Workflow | NVD API Key | Notes |
|----------|---------------------|-------------|-------|
| Learning/Development | `simple-ci.yml` | Not needed | Fastest, covers all testing |
| Open Source Project | `ci-cd.yml` | Optional | Security scanning builds trust |
| Enterprise/Production | `ci-cd.yml` | Required | Complete security coverage |
| Time-constrained CI | `simple-ci.yml` | Not needed | Quick feedback loop |

## 🚨 Troubleshooting

### Issue: "NVD API Key warning"
**Solution**: Either add API key or use `simple-ci.yml`

### Issue: "Trivy scan timeout"  
**Solution**: Add `timeout: '15m'` to Trivy step

### Issue: "Cypress tests failing"
**Solution**: Tests run with `continue-on-error: true`, check artifacts for details

### Issue: "Docker build slow"
**Solution**: Docker layer caching is enabled, subsequent builds will be faster

## 📝 Customization

You can customize the workflows by:
- Changing test timeouts
- Adding code quality checks (SonarQube, CodeClimate)
- Adding deployment steps
- Modifying artifact retention periods
- Adding notification steps (Slack, Teams)

## 🎉 Quick Start

1. **Choose your workflow** based on the table above
2. **Commit and push** to trigger the first run
3. **Check Actions tab** to see results
4. **Download artifacts** if tests fail
5. **Add NVD API key** if using full pipeline (optional)

Your CI/CD pipeline is now ready to automatically test and build your Spring Boot application with Docker and Cypress! 🚀
